package com.example.nouran.space.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nouran.space.Data.News;
import com.example.nouran.space.FavoriteActivity;
import com.example.nouran.space.NewsDetailActivity;
import com.example.nouran.space.NewsWrapper;
import com.example.nouran.space.R;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.myHolder> {

    private Context context;
    private ArrayList<News> newsList;
    private String thumbnail;
    private String ac;


    public NewsAdapter(Context context, ArrayList<News> newsList, String ac) {
        this.context = context;
        this.newsList = newsList;
        this.ac = ac;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new myHolder(view);
    }


    @Override
    public void onBindViewHolder(myHolder holder, final int position) {


        Log.i("LastNewsFragmentErrorrr", "LLLL");
        holder.newsTxt.setText(newsList.get(position).getName());


        holder.newsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("ClickedItem", newsList.get(position).getNews_id());
                Log.d("CONTEXT TEST", context + "");
                intent.putExtra("ClickedItem_context", ac);
                context.startActivity(intent);
            }
        });
        if (ac.equals("Last News")) {
            requestDetailedData(holder, newsList.get(position).getNews_id());
        }
        else if (ac.equals("Favorite"))
        {
            Picasso.get().load(newsList.get(position).getThumbnail()).placeholder(R.drawable.rocket).into(holder.newsImage);
        }
    }

    private String requestDetailedData(final myHolder holder, final String id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://hubblesite.org/api/v3/news_release/" + id;
        Log.i("LastNewsFragmentError", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject member = new JSONObject(response);
                            thumbnail = member.getString("thumbnail");
                                Picasso.get().load(thumbnail).placeholder(R.drawable.rocket).into(holder.newsImage);

//                            new Picasso.Builder(context)
//                                    .downloader(new OkHttp3Downloader(context, Integer.MAX_VALUE))
//                                    .build()
//                                    .load(thumbnail)
//                                    .into(holder.newsImage);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("LastNewsFragmentError", error + "");
            }
        });
        queue.add(stringRequest);
        return thumbnail;
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class myHolder extends RecyclerView.ViewHolder {

        TextView newsTxt;
        ImageView newsImage;
        LinearLayout newsLayout;

        public myHolder(final View itemView) {
            super(itemView);
            newsTxt = itemView.findViewById(R.id.news_txt);
            newsImage = itemView.findViewById(R.id.news_image);
            newsLayout = itemView.findViewById(R.id.news_layout_container);
        }
    }
}