package com.example.nouran.space;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
//import android.support.design.widget.TabLayout;
//import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private SectionPagerAdapter mSectionPagerAdapter;
    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.main_page_toobar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Space");

        mViewPager = findViewById(R.id.tabPager);
        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());//Return the FragmentManager for interacting with fragments associated with this activity.
        mViewPager.setAdapter(mSectionPagerAdapter);

        mTabLayout = findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        intialize();
    }

    public void intialize() {
//        dayTxt = findViewById(R.id.single_dayW_txt);
//        timeTxt = findViewById(R.id.single_timeW_txt);
//        conditionTxt = findViewById(R.id.single_conditionTextW_txt);
//        tempTxt = findViewById(R.id.single_celW_txt);
//        icon = findViewById(R.id.single_statusW_image);
//        location_txt = findViewById(R.id.location_text);
//
//
//        mToolbar = findViewById(R.id.main_page_toobar);
//        setSupportActionBar(mToolbar);
//        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//
//
//        actionBar.setDisplayShowCustomEnabled(true);
//
//        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View actionBarView = layoutInflater.inflate(R.layout.custom_bar, null);
//        actionBar.setCustomView(actionBarView);
//
//        toolbarText = findViewById(R.id.tool_bar_text);
//
//        mLocationBtn = findViewById(R.id.ChatAddButton);
//
//        recyclerView = findViewById(R.id.days_recycler_view);
//        details = new ArrayList<>();
//        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);
//
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
//                layoutManager.getOrientation());
//
//        recyclerView.addItemDecoration(dividerItemDecoration);
//
    }
}