package com.example.nouran.space;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class SectionPagerAdapter extends FragmentPagerAdapter {

    private Context context ;
    public SectionPagerAdapter(FragmentManager fm , Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LastNewsFragment newsFragment = new LastNewsFragment();
                return newsFragment;
            case 1:
                ExploreFragment exploreFragment = new ExploreFragment();
                return exploreFragment;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.All_News);
            case 1:
                return context.getString(R.string.Explore);
            default:
                return null;
        }

    }
}

