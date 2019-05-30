package com.huojitang.leaf;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * （照搬主页）
 * @author Mookiepiece
 */
public class EditTagFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"背景色","图标"};
    private ArrayList<Fragment> fragments;

    public EditTagFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
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