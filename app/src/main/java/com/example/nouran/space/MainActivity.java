package com.example.nouran.space;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.nouran.space.Data.MainViewModel;
import com.example.nouran.space.Data.News;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
//import android.support.design.widget.TabLayout;
//import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private SectionPagerAdapter mSectionPagerAdapter;
    private TabLayout mTabLayout;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mToolbar = findViewById(R.id.main_page_toobar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Space News");

        mViewPager = findViewById(R.id.tabPager);
        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());//Return the FragmentManager for interacting with fragments associated with this activity.
        mViewPager.setAdapter(mSectionPagerAdapter);

        mTabLayout = findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite_menu: {
                 MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
                viewModel.getNews().observe(this, new Observer<List<News>>() {
                    @Override
                    public void onChanged(@Nullable List<News> m) {

                        ArrayList<News> tmp = new ArrayList<News>(m);

                        Intent intent = new Intent(MainActivity.this , FavoriteActivity.class);
                        intent.putParcelableArrayListExtra("fav_items",tmp);
                        startActivity(intent);
                    }
                });
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}