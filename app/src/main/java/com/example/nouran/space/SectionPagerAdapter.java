package com.example.nouran.space;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class SectionPagerAdapter extends FragmentPagerAdapter {

    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
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
                return "All News";
            case 1:
                return "Explore";
            default:
                return null;
        }

    }
}

