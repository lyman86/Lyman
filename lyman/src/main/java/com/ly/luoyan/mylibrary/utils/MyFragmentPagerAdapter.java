package com.ly.luoyan.mylibrary.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

/**
 * Created by luoyan on 2017/4/13.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment>fragments;
    public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment>fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
