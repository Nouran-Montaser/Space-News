package com.example.nouran.space;

import com.example.nouran.space.Data.News;

public class NewsWrapper {

    private static final NewsWrapper ourInstance = new NewsWrapper();
    News news;

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public static NewsWrapper getInstance() {
        return ourInstance;
    }

    private NewsWrapper() {
    }
}