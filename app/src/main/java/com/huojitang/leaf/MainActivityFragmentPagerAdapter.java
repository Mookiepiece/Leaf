package com.huojitang.leaf;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MainActivityFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"帐单","心愿单"};
    private ArrayList<Fragment> fragments;

    public MainActivityFragmentPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
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