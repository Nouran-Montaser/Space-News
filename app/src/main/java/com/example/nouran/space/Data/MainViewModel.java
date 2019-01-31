package com.example.nouran.space.Data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;







import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<News>> news;

    public MainViewModel(@NonNull Application application) {
        super(application);
        MyDataBase myDataBase = MyDataBase.getAppDatabase(this.getApplication());
        news = myDataBase.newsADO().getAll();
    }


    public LiveData<List<News>> getNews() {
        return news;
    }

    public void setNews(LiveData<List<News>> news) {
        this.news = news;
    }

}
