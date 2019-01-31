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



//public OkHttpClient httpClient;

//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        if (BuildConfig.DEBUG) {
//
//            // Create an InitializerBuilder
//            Stetho.InitializerBuilder initializerBuilder = Stetho.newInitializerBuilder(this);
//
//            // Enable Chrome DevTools
//            initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this));
//
//            // Enable command line interface
//            initializerBuilder.enableDumpapp(Stetho.defaultDumperPluginsProvider(this));
//
//            // Use the InitializerBuilder to generate an Initializer
//            Stetho.Initializer initializer = initializerBuilder.build();
//
//            // Initialize Stetho with the Initializer
//            Stetho.initialize(initializer);
//
//            //Initialize Stetho Interceptor into OkHttp client
////            httpClient = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();
////        } else {
////            httpClient = new OkHttpClient();
////        }
//
//        //Initialize Picasso
////        Picasso picasso = new Picasso.Builder(this).downloader(new OkHttp3Downloader(httpClient)).build();
////        Picasso.setSingletonInstance(picasso);
//
//    }
//}
//}