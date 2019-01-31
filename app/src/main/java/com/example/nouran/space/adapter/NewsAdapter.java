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

import com.example.nouran.space.Data.News;
import com.example.nouran.space.NewsDetailActivity;
import com.example.nouran.space.NewsWrapper;
import com.example.nouran.space.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.myHolder> {

    private Context context;
    private ArrayList<News> newsList;

    public NewsAdapter(Context context, ArrayList<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new myHolder(view);
    }


    @Override
    public void onBindViewHolder(myHolder holder, final int position) {


        Log.i("LastNewsFragmentError","LLLL");
        holder.newsTxt.setText(newsList.get(position).getName());

        Picasso.get().load(newsList.get(position).getThumbnail()).into(holder.newsImage);

        holder.newsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("ClickedItem", newsList.get(position));
                context.startActivity(intent);
            }
        });

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