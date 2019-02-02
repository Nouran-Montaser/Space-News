package com.example.nouran.space;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.SubtitleCollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nouran.space.Data.AppExecutors;
import com.example.nouran.space.Data.MainViewModel;
import com.example.nouran.space.Data.MyDataBase;
import com.example.nouran.space.Data.News;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class NewsDetailActivity extends AppCompatActivity {

    private SubtitleCollapsingToolbarLayout collapsingToolbarLayout;

    private String newsID;
    private TextView bodyView;
    private ImageView imageView;
    private String news_url;
    private ImageView mFavBtn;
    private boolean ISFAVORITE = false;
    private News mNews;
    private MyDataBase mDb;
    private String ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        newsID = getIntent().getStringExtra("ClickedItem");
        ac = getIntent().getStringExtra("ClickedItem_context");

        requestDetailedData();
        mDb = MyDataBase.getAppDatabase(getApplicationContext());
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);

        Toolbar toolbar = findViewById(R.id.news_toolbarr);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        ab.setDisplayShowTitleEnabled(false); // disable the default title element here (for centered title)

        mFavBtn = findViewById(R.id.fav_btn);


        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getNews().observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(@Nullable List<News> m) {
                assert m != null;
                for (int i = 0; i < m.size(); i++) {
                    if (m.get(i).getNews_id().equals(newsID)) {
                        mFavBtn.setImageResource(R.drawable.ic_favorite_black_24dp);
                        ISFAVORITE = true;
                        collapsingToolbarLayout.setTitle(getString(R.string.app_name));
                        collapsingToolbarLayout.setSubtitle(m.get(i).getMission());
                        bodyView.setText(m.get(i).getAbstrac());
                        Picasso.get().load(m.get(i).getThumbnail()).into(imageView);
//                        Toast.makeText(NewsDetailActivity.this, "FAVORITE", Toast.LENGTH_SHORT).show();
                        mNews = new News(m.get(i).getThumbnail(),m.get(i).getAbstrac(),m.get(i).getNews_id(),m.get(i).getUrl(),m.get(i).getMission()
                                ,m.get(i).getPublication(),m.get(i).getName());

                    }
                }
            }
        });


        mFavBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AppExecutors.getsInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (ISFAVORITE) {
                            ISFAVORITE = false;
                            mDb.newsADO().deleteNews(mNews);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mFavBtn.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                                    Log.i("FAVBTN", "UNFAV");
                                }
                            });
                        } else {
                            mDb.newsADO().insertNews(mNews);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ISFAVORITE = true;
                                    mFavBtn.setImageResource(R.drawable.ic_favorite_black_24dp);
                                    Log.i("FAVBTN", "FAV");
                                }
                            });
                        }
                    }
                });
            }
        });

        bodyView = findViewById(R.id.article_body);
        imageView = findViewById(R.id.photo);
        collapsingToolbarLayout.setExpandedTitleTextColor(R.color.colorTitlte);
        FloatingActionButton openInBrowserBtn = findViewById(R.id.browser_fab);
//        requestDetailedData();
        openInBrowserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (news_url != null) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(news_url));
                    startActivity(i);
                }
            }
        });
    }

    private void requestDetailedData() {

        RequestQueue queue = Volley.newRequestQueue(NewsDetailActivity.this);
        final String url = "http://hubblesite.org/api/v3/news_release/" + newsID;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject member = new JSONObject(response);
                            String name = member.getString("name");
                            String news_id = member.getString("news_id");
                            String publication = member.getString("publication");
                            String mission = member.getString("mission");
                            String _abstract = member.getString("abstract");
                            String thumbnail = member.getString("thumbnail");
                            news_url = member.getString("url");
                            collapsingToolbarLayout.setTitle(getString(R.string.app_name));
                            collapsingToolbarLayout.setSubtitle(mission);
                            bodyView.setText(_abstract);
                            Picasso.get().load(thumbnail).into(imageView);
                            mNews = new News(thumbnail, _abstract, news_id, url, mission, publication, name);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("NewsDetailError", error + "");
                if (ac.equals("Last News")) {
                    Toast.makeText(NewsDetailActivity.this, getString(R.string.error_in_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });
        queue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.widget_menu: {

                SharedPreferences.Editor sharedPrefsEditor;
                final String MY_PREFS_NAME = "MyPrefsFile";

                sharedPrefsEditor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

                sharedPrefsEditor.putString("name", mNews.getName());

                sharedPrefsEditor.putString("NEWS_PREF", mNews.getAbstrac());
                sharedPrefsEditor.apply();

                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
