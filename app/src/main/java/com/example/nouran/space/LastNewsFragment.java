package com.example.nouran.space;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.net.ParseException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nouran.space.Data.Client;
import com.example.nouran.space.Data.News;
import com.example.nouran.space.adapter.NewsAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class LastNewsFragment extends Fragment {

    private RecyclerView mNewsList;
    private ArrayList<News> details;
    private ArrayList<String> _IDList;
    private NewsAdapter newsAdapter;

    public LastNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mMainView;

        mMainView = inflater.inflate(R.layout.fragment_last_news, container, false);

        mNewsList = mMainView.findViewById(R.id.news_recyclerView);

        mNewsList.setHasFixedSize(true);
        mNewsList.setLayoutManager(new LinearLayoutManager(getContext()));

        return mMainView;
    }

    @Override
    public void onStart() {
        super.onStart();

        details = new ArrayList<>();
        _IDList = new ArrayList<>();
        requestData();
    }

    private void requestData() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://hubblesite.org/api/v3/news";


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray all = new JSONArray(response);
                            String name, id;
                            for (int i = 0; i < all.length(); i++) {
                                JSONObject member = all.getJSONObject(i);
//                                name = member.getString("name");
                                id = member.getString("news_id");
                                _IDList.add(id);
                                Log.i("LastNewsFragmentError", "hhhh1");
                            }
                            if (requestDetailedData(_IDList) == null)
                                Log.i("ERROR ", "ERROR IN CONNECTION");
                            else {
                                Log.i("LastNewsFragmentError", "hhhhoooppp");

                                newsAdapter = new NewsAdapter(getContext(), requestDetailedData(_IDList));
                                mNewsList.setAdapter(newsAdapter);
                            }
                            Log.i("LastNewsFragmentError", "hhhh");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("LastNewsFragmentError", error + "");
                Toast.makeText(getActivity(), "Error in connection", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    private ArrayList<News> requestDetailedData(final ArrayList<String> idList) {
        Log.i("LastNewsFragmentError", "hhhh3");

        for (int i = 0; i < idList.size(); i++) {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(getContext());
            String url = "http://hubblesite.org/api/v3/news_release/" + idList.get(i);
            Log.i("LastNewsFragmentError", url);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject member = new JSONObject(response);

                                String name = member.getString("name");
                                String news_id = member.getString("news_id");
//                            String url = member.getString("url");
//                            String publication = member.getString("publication");
//                            String mission = member.getString("mission");
                                String _abstract = member.getString("abstract");
                                String thumbnail = member.getString("thumbnail");
                                Log.i("LastNewsFragmentError", "hhhh5   " + news_id);
//                            JSONArray  release_videos = member.getJSONArray("release_videos");
//                            String[] videos = new String[release_videos.length()];
//                            for(int i=0;i<release_videos.length();i++)
//                            {
//                                videos[i] = release_videos.get(i).toString();
//                            }
                                details.add(new News(thumbnail, new String[]{}, _abstract, news_id, "", "", "", name));
                                Log.i("LastNewsFragmentError", details.size() + "    " + details.get(details.size() - 1));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("LastNewsFragmentError", error + "");
                    Toast.makeText(getActivity(), "Error in connection", Toast.LENGTH_SHORT).show();
                }
            });
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
        return details;
    }


}
