package com.huojitang.leaf;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * （照搬主页）
 * @author Mookiepiece
 */
public class LeafFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles;
    private ArrayList<Fragment> fragments;

    public LeafFragmentPagerAdapter(FragmentManager fm, String[] mTitles, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
        this.mTitles=mTitles;
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