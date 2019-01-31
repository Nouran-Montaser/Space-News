package com.example.nouran.space.Data;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import javax.annotation.Nonnull;

@Entity(tableName = "News")
public class News implements Parcelable {

    private String thumbnail;

    private String abstrac;

    @NonNull
    @PrimaryKey
    private String news_id;

    private String url;

    private String mission;

    private String publication;

    private String name;


    @Ignore
    public News() {
    }

    public News(String thumbnail, String abstrac, String news_id, String url, String mission, String publication, String name) {
        this.thumbnail = thumbnail;
        this.abstrac = abstrac;
        this.news_id = news_id;
        this.url = url;
        this.mission = mission;
        this.publication = publication;
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAbstrac() {
        return abstrac;
    }

    public void setAbstrac(String abstrac) {
        this.abstrac = abstrac;
    }

    @NonNull
    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(@NonNull String news_id) {
        this.news_id = news_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClassPojo [thumbnail = " + thumbnail + ", thumbnail_2x = " + ", abstrac = " + abstrac + ", news_id = " + news_id + ", url = " + url + ", mission = " + mission + " , publication = " + publication + ", name = " + name + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.thumbnail);
        dest.writeString(this.abstrac);
        dest.writeString(this.news_id);
        dest.writeString(this.url);
        dest.writeString(this.mission);
        dest.writeString(this.publication);
        dest.writeString(this.name);
    }

    protected News(Parcel in) {
        this.thumbnail = in.readString();
        this.abstrac = in.readString();
        this.news_id = in.readString();
        this.url = in.readString();
        this.mission = in.readString();
        this.publication = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}