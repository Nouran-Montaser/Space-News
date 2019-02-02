package com.example.nouran.space;


import android.content.Context;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nouran.space.Data.Videos;
import com.example.nouran.space.adapter.ExploreAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ExploreFragment extends Fragment {


    private RecyclerView mExploreList;
    private ArrayList<Videos> mExplore;
    private ExploreAdapter exploreAdapter;
    private LinearLayout conecctionLayout;
    private ProgressBar mProgressbar;
    private long currentVisiblePosition = 0;

    public ExploreFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mMainView = inflater.inflate(R.layout.fragment_explore, container, false);
        mExploreList = mMainView.findViewById(R.id.news_recyclerView);

        mExploreList.setHasFixedSize(true);

        mExploreList.setLayoutManager(new GridLayoutManager(getContext(), 2));

        conecctionLayout = mMainView.findViewById(R.id.EX_connection_layout);
        TextView conecctionFTxt = mMainView.findViewById(R.id.EX_connection_txt1);
        TextView conecctionSTxt = mMainView.findViewById(R.id.EX_connection_txt2);
        conecctionSTxt.setPaintFlags(conecctionSTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        conecctionSTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInternetConnection();
            }
        });

        mProgressbar = mMainView.findViewById(R.id.explore_Progressbar);

        return mMainView;
    }

    public void checkInternetConnection() {

//        mExploreList.setVisibility(View.VISIBLE);
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean connected = false;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
            conecctionLayout.setVisibility(View.GONE);
            mProgressbar.setVisibility(View.VISIBLE);
            mExploreList.setVisibility(View.GONE);
            requestData();
        } else {
            connected = false;
            conecctionLayout.setVisibility(View.VISIBLE);
            mExploreList.setVisibility(View.GONE);
            mProgressbar.setVisibility(View.GONE);
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        mExplore = new ArrayList<>();
        checkInternetConnection();
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
                            String imgUrl, id, name;
                            for (int i = 0; i < all.length(); i++) {
                                JSONObject member = all.getJSONObject(i);
                                name = member.getString("name");
                                id = member.getString("id");
                                imgUrl = member.getString("image");

                                String highImgUrl = imgUrl.replace("thumb_low_", "");

                                mExplore.add(new Videos(id, name, highImgUrl));
                            }
                            exploreAdapter = new ExploreAdapter(getContext(), mExplore);
                            mProgressbar.setVisibility(View.GONE);
                            mExploreList.setVisibility(View.VISIBLE);
                            mExploreList.setAdapter(exploreAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ExploreFragmentError", error + "");
                mProgressbar.setVisibility(View.GONE);
                conecctionLayout.setVisibility(View.VISIBLE);
                mExploreList.setVisibility(View.GONE);
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    @Override
    public void onResume() {
        super.onResume();
        mExploreList.getLayoutManager().scrollToPosition((int) currentVisiblePosition);
        currentVisiblePosition = 0;
    }

    @Override
    public void onPause() {
        super.onPause();
        currentVisiblePosition = ((LinearLayoutManager) mExploreList.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

}
