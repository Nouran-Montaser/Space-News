package com.example.nouran.space;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.SubtitleCollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nouran.space.Data.News;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsDetailActivity extends AppCompatActivity {

    private SubtitleCollapsingToolbarLayout collapsingToolbarLayout;

    private Toolbar toolbar;
    private String newsID;
    private TextView bodyView;
    private ImageView imageView;
    private FloatingActionButton openInBrowserBtn;
    private String news_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        newsID = getIntent().getStringExtra("ClickedItem");
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);

        toolbar = findViewById(R.id.news_toolbarr);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        ab.setDisplayShowTitleEnabled(false); // disable the default title element here (for centered title)


        bodyView = findViewById(R.id.article_body);
        imageView = findViewById(R.id.photo);
        collapsingToolbarLayout.setExpandedTitleTextColor(R.color.colorTitlte);
        openInBrowserBtn = findViewById(R.id.browser_fab);
        requestDetailedData();
        openInBrowserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (news_url != null) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(news_url));
                    startActivity(i);
                }
            }});
    }


    private void requestDetailedData() {

        RequestQueue queue = Volley.newRequestQueue(NewsDetailActivity.this);
        String url = "http://hubblesite.org/api/v3/news_release/" + newsID;

        Log.i("PPPPPPPPPPPPPPPPPPPP",url);
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
                            Log.i("PPPPPPPPPPPP", "hhhh5   " + news_id+"   "+_abstract);
                            collapsingToolbarLayout.setTitle("Space News");
                            collapsingToolbarLayout.setSubtitle(mission);
                            bodyView.setText(_abstract);
                            Picasso.get().load(thumbnail).into(imageView);
                            JSONArray release_videos = member.getJSONArray("release_videos");
                            String[] videos = new String[release_videos.length()];
                            for (int i = 0; i < release_videos.length(); i++) {
                                videos[i] = release_videos.get(i).toString();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("LastNewsFragmentError", error + "");
                Toast.makeText(NewsDetailActivity.this, "Error in connection", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }


}
