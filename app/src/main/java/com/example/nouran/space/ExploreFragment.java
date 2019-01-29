package com.example.nouran.space;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nouran.space.Data.News;
import com.example.nouran.space.Data.Videos;
import com.example.nouran.space.adapter.ExploreAdapter;
import com.example.nouran.space.adapter.NewsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {


    private RecyclerView mExploreList;
    private ArrayList<Videos> mExplore;
    private ExploreAdapter exploreAdapter ;

    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mMainView =  inflater.inflate(R.layout.fragment_explore, container, false);
        mExploreList = mMainView.findViewById(R.id.news_recyclerView);

        mExploreList.setHasFixedSize(true);

        mExploreList.setLayoutManager(new GridLayoutManager(getContext(),2));

        return mMainView;
    }



    @Override
    public void onStart() {
        super.onStart();

        mExplore = new ArrayList<>();
        requestData();
    }


    private void requestData() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://hubblesite.org/api/v3/videos/";


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray all = new JSONArray(response);
                            String imgUrl, id,name;
                            for (int i = 0; i < all.length(); i++) {
                                JSONObject member = all.getJSONObject(i);
                                name = member.getString("name");
                                id = member.getString("id");
                                imgUrl = member.getString("image");

                                String highImgUrl = imgUrl.replace("thumb_low_","");

                                mExplore.add(new Videos(id,name,highImgUrl));
                            }
                            exploreAdapter = new ExploreAdapter(getContext(), mExplore);
                            mExploreList.setAdapter(exploreAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ExploreFragmentError", error + "");
                Toast.makeText(getActivity(), "Error in connection", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }


}
