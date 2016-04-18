package com.alexcrijman.stsapp10.adaptors;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> lFragmentList = new ArrayList<>();
    private final List<String> lFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        return lFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return lFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        lFragmentList.add(fragment);
        lFragmentTitleList.add(title);
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return lFragmentTitleList.get(position);
    }


}
