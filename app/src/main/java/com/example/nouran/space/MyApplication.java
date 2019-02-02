package com.example.nouran.space;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}