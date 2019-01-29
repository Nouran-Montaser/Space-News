package com.example.nouran.space.Data;


public class News
{
    private String thumbnail;

    private String[] release_images;

    private String[] release_videos;

    private String thumbnail_retina;

    private String thumbnail_2x;

    private String thumbnail_1x;

    private String mAbstract;

    private String keystone_image_1x;

    private String keystone_image_2x;

    private String news_id;

    private String url;

    private String mission;

    private String credits;

    private String publication;

    private String name;

    public News(String thumbnail, String[] release_videos, String mAbstract, String news_id, String url, String mission, String publication, String name) {
        this.thumbnail = thumbnail;
        this.release_videos = release_videos;
        this.mAbstract = mAbstract;
        this.news_id = news_id;
        this.url = url;
        this.mission = mission;
        this.publication = publication;
        this.name = name;
    }

    public String getThumbnail ()
    {
        return thumbnail;
    }

    public void setThumbnail (String thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public String[] getRelease_images ()
    {
        return release_images;
    }

    public void setRelease_images (String[] release_images)
    {
        this.release_images = release_images;
    }

    public String getThumbnail_retina ()
    {
        return thumbnail_retina;
    }

    public void setThumbnail_retina (String thumbnail_retina)
    {
        this.thumbnail_retina = thumbnail_retina;
    }

    public String getThumbnail_2x ()
    {
        return thumbnail_2x;
    }

    public void setThumbnail_2x (String thumbnail_2x)
    {
        this.thumbnail_2x = thumbnail_2x;
    }

    public String getThumbnail_1x ()
    {
        return thumbnail_1x;
    }

    public void setThumbnail_1x (String thumbnail_1x)
    {
        this.thumbnail_1x = thumbnail_1x;
    }

    public String getAbstract ()
    {
        return mAbstract;
    }

    public void setAbstract (String mAbstract)
    {
        this.mAbstract = mAbstract;
    }

    public String getKeystone_image_1x ()
    {
        return keystone_image_1x;
    }

    public void setKeystone_image_1x (String keystone_image_1x)
    {
        this.keystone_image_1x = keystone_image_1x;
    }

    public String getKeystone_image_2x ()
    {
        return keystone_image_2x;
    }

    public void setKeystone_image_2x (String keystone_image_2x)
    {
        this.keystone_image_2x = keystone_image_2x;
    }

    public String getNews_id ()
    {
        return news_id;
    }

    public void setNews_id (String news_id)
    {
        this.news_id = news_id;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getMission ()
    {
        return mission;
    }

    public void setMission (String mission)
    {
        this.mission = mission;
    }

    public String getCredits ()
    {
        return credits;
    }

    public void setCredits (String credits)
    {
        this.credits = credits;
    }

    public String getPublication ()
    {
        return publication;
    }

    public void setPublication (String publication)
    {
        this.publication = publication;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [thumbnail = "+thumbnail+", release_images = "+release_images+", thumbnail_retina = "+thumbnail_retina+", thumbnail_2x = "+thumbnail_2x+", thumbnail_1x = "+thumbnail_1x+", abstract = "+mAbstract+", keystone_image_1x = "+keystone_image_1x+", keystone_image_2x = "+keystone_image_2x+", news_id = "+news_id+", url = "+url+", mission = "+mission+", credits = "+credits+", publication = "+publication+", name = "+name+"]";
    }

    public String[] getRelease_videos() {
        return release_videos;
    }

    public void setRelease_videos(String[] release_videos) {
        this.release_videos = release_videos;
    }
}