package com.example.nouran.space;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nouran.space.Data.News;
import com.example.nouran.space.adapter.NewsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class LastNewsFragment extends Fragment {

    private RecyclerView mNewsList;
    private ArrayList<News> details;
    private ArrayList<String> _IDList;
    private NewsAdapter newsAdapter;
    private LinearLayout conecctionLayout;
    private ProgressBar mProgressbar;
    private TextView conecctionFTxt;
    private TextView conecctionSTxt;
    private boolean connected = false;
    private LinearLayoutManager linearLayoutManager;
    private long currentVisiblePosition = 0;


    public LastNewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mMainView;

        mMainView = inflater.inflate(R.layout.fragment_last_news, container, false);

        mNewsList = mMainView.findViewById(R.id.news_recyclerView);
        conecctionLayout = mMainView.findViewById(R.id.connection_layout);
        conecctionFTxt = mMainView.findViewById(R.id.connection_txt1);
        conecctionSTxt = mMainView.findViewById(R.id.connection_txt2);
        conecctionSTxt.setPaintFlags(conecctionSTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        conecctionSTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInternetConnection();
            }
        });

        mProgressbar = mMainView.findViewById(R.id.lastnews_Progressbar);

        linearLayoutManager = new LinearLayoutManager(getContext());
        mNewsList.setHasFixedSize(true);
        mNewsList.setLayoutManager(linearLayoutManager);


        return mMainView;
    }

    public void checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
            conecctionLayout.setVisibility(View.GONE);
            mProgressbar.setVisibility(View.VISIBLE);
            mNewsList.setVisibility(View.GONE);
            requestData();
        } else {
            connected = false;
            conecctionLayout.setVisibility(View.VISIBLE);
            mNewsList.setVisibility(View.GONE);
            mProgressbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        details = new ArrayList<>();
        _IDList = new ArrayList<>();
        checkInternetConnection();
//        requestData();
    }

    private void requestData() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://hubblesite.org/api/v3/news";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray all = new JSONArray(response);
                            String id;
                            String mName;

                            for (int i = 0; i < all.length(); i++) {
                                JSONObject member = all.getJSONObject(i);
                                id = member.getString("news_id");
                                mName = member.getString("name");
                                _IDList.add(id);
                                details.add(new News("", "", id, "", "", "", mName));
                            }

                            mProgressbar.setVisibility(View.GONE);
                            conecctionLayout.setVisibility(View.GONE);
                            mNewsList.setVisibility(View.VISIBLE);
                            newsAdapter = new NewsAdapter(getContext(), details, "Last News");
                            mNewsList.setAdapter(newsAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("LastNewsFragmentError", error + "");
                conecctionLayout.setVisibility(View.VISIBLE);
                mProgressbar.setVisibility(View.GONE);
                mNewsList.setVisibility(View.GONE);
            }
        });
        queue.add(stringRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
         mNewsList.getLayoutManager().scrollToPosition((int) currentVisiblePosition);
        currentVisiblePosition = 0;
    }

    @Override
    public void onPause() {
        super.onPause();
        currentVisiblePosition = ((LinearLayoutManager) mNewsList.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }
}
