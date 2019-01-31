package com.example.nouran.space.Data;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class LoadNewsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final MyDataBase mDb;
    private final int news_id;

    public LoadNewsViewModelFactory(MyDataBase mDb, int news_id) {
        this.mDb = mDb;
        this.news_id = news_id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoadNewsViewModel(mDb, news_id);
    }
}