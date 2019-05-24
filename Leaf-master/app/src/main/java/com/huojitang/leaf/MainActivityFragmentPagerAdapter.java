package com.huojitang.leaf;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainActivityFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"帐单","心愿单"};
    public MainActivityFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==1){
            return new WishFragment();
        }
        return new ListFragment();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }
    @Override
    public CharSequence getPageTitle(int position){
        return mTitles[position];
    }
}