package com.tokopedia.toped;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.tokopedia.toped.base.MainActivity;
import com.tokopedia.toped.fragment.FragmentListing;
import com.tokopedia.toped.fragment.FragmentOnMyWay;

import java.util.ArrayList;

/**
 * Created by Tkpd_Eka on 8/29/2015.
 */
public class ListingActivity extends MainActivity{

    private class ListingPagerAdapter extends FragmentPagerAdapter{

        public ListingPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position];
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    View mainView;
    TabLayout tabLayout;
    ViewPager pager;
    String[] CONTENT = {"LISTING", "ON MY WAY"};
    ArrayList<Fragment> fragments;
    ListingPagerAdapter adapter;

    @Override
    protected void inflateMainView(int mainViewId) {
        createView(mainViewId);
        tabLayout = (TabLayout)mainView.findViewById(R.id.tab);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        pager = (ViewPager)mainView.findViewById(R.id.pager);
        fragments = new ArrayList<>();
        fragments.add(new FragmentListing());
        fragments.add(new FragmentOnMyWay());
        adapter = new ListingPagerAdapter(getFragmentManager());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }

    private void createView(int mainViewId){
        FrameLayout frame = (FrameLayout)findViewById(mainViewId);
        mainView = LayoutInflater.from(this).inflate(R.layout.activity_listing, null, false);
        frame.addView(mainView);
    }
}