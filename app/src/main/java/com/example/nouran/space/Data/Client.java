package com.example.nouran.space.Data;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Client {
//    List<Client> reposForUser(String user);
    /*Retrofit get annotation with our URL
       And our method that will return us the list of students info
    */

    @GET("api/v3/news")
    Call<News> getRes();
    //public void getres(Callback<Weather> response);

//    @GET("forecast.json?key=66411d4e534944b3bdb193244182703&days=7")
//@POST("current.json?key=66411d4e534944b3bdb193244182703")
//    Call<News> addlocation(@Query("q") String name);

}
