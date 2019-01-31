package com.example.nouran.space.Data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

class LoadNewsViewModel extends ViewModel {

    private LiveData<News> newsLiveData;

    public LiveData<News> getNewsLiveData() {
        return newsLiveData;
    }

    public LoadNewsViewModel(MyDataBase mDb, int id) {
        newsLiveData = mDb.newsADO().loadNewsById(id);
    }
}
