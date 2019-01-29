package com.example.nouran.space;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nouran.space.Data.Videos;
import com.example.nouran.space.adapter.ExploreAdapter;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class VideoActivity extends AppCompatActivity {

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private long position;
    private TrackSelector trackSelector;
    private BandwidthMeter bandwidthMeter;
    private TrackSelection.Factory videoTrackSelectionFactory;
    private LoadControl loadControl;
    private DataSource.Factory dataSourceFactory;
    private ExtractorsFactory extractorsFactory;
    private MediaSource videoSource;
    private String _ID;
    private String video_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        _ID = getIntent().getStringExtra("ClickedItem");

        mPlayerView = findViewById(R.id.video_view);
        requestData(_ID);
    }

    private void requestData(String id) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://hubblesite.org/api/v3/video/" + id;


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject member = null;
                        try {
                            member = new JSONObject(response);

//                            JSONObject html_video = new JSONObject(member.getString("html_5_video"));
//                            video_url = html_video.getString("video_url");
                            JSONArray video_files = new JSONArray(member.getString("video_files"));
                            JSONObject urlObject = video_files.getJSONObject(0);
                            video_url = urlObject.getString("file_url");

                            initializePlayer(Uri.parse(video_url));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ExploreFragmentError", error + "");
                Toast.makeText(getApplicationContext(), "Error in connection", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    private void initializePlayer(Uri mediaUri) {
        // Create a default TrackSelector
        bandwidthMeter = new DefaultBandwidthMeter();
        videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        loadControl = new DefaultLoadControl();

        //Initialize the player
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector, loadControl);

        //Initialize simpleExoPlayerView
        mPlayerView.setPlayer(mExoPlayer);

        // Produces DataSource instances through which media data is loaded.
        dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), Util.getUserAgent(getApplicationContext(), "BakingApp"));

        // Produces Extractor instances for parsing the media data.
        extractorsFactory = new DefaultExtractorsFactory();

        // This is the MediaSource representing the media to be played.
        videoSource = new ExtractorMediaSource(mediaUri, dataSourceFactory, extractorsFactory, null, new ExtractorMediaSource.EventListener() {
            @Override
            public void onLoadError(IOException e) {

            }
        });
        // Prepare the player with the source.
        mExoPlayer.prepare(videoSource);
        mExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        if (mExoPlayer != null)
            mExoPlayer.release();
        mExoPlayer = null;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("SELECTED_POSITION", position);
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            position = savedInstanceState.getLong("SELECTED_POSITION", C.TIME_UNSET);
        }
        Log.i("VIDEO ACTIVTY", "onRestoreInstanceState: " + position);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            position = mExoPlayer.getCurrentPosition();
            mExoPlayer.getPlayWhenReady();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(Uri.parse(video_url));
            mExoPlayer.seekTo(position);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (video_url != null) {
            if (Util.SDK_INT <= 23 || mPlayerView == null)
                initializePlayer(Uri.parse(video_url));
        }
    }


    private void releasePlayer() {
        if (mExoPlayer != null) {
            position = mExoPlayer.getCurrentPosition();
            mExoPlayer.stop();
        }
    }

}