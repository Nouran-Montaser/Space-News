package com.example.nouran.space.Data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
public interface NewsADO {

    @Query("SELECT * FROM News")
    LiveData<List<News>> getAll();

    @Insert(onConflict = REPLACE)
    void insertNews (News news);

    @Query("SELECT * FROM News WHERE news_id = :news_id")
    LiveData<News> loadNewsById (int news_id);

    @Delete
    void deleteNews(News news);

}
