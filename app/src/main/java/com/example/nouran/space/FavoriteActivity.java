package com.example.nouran.space;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.nouran.space.Data.News;
import com.example.nouran.space.adapter.NewsAdapter;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    private ArrayList<News> tmp;
    private NewsAdapter newsAdapter;
    private RecyclerView mNewsList;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        mToolbar = findViewById(R.id.fav_page_toobar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        getSupportActionBar().setTitle("Favorite News");

        tmp = new ArrayList<>();
        mNewsList = findViewById(R.id.fav_recycler_view);
        mNewsList.setHasFixedSize(true);
        mNewsList.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this));

        tmp = getIntent().getParcelableArrayListExtra("fav_items");

        Log.i("favActivity", tmp.toString());

        newsAdapter = new NewsAdapter(FavoriteActivity.this, tmp);
        mNewsList.setAdapter(newsAdapter);


    }
}
