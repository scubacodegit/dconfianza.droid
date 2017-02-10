package com.scubacode.dconfianza;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    int userID;

    public PagerAdapter(FragmentManager fm, int NumOfTabs, int userID) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.userID=userID;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                SearchFragment tab1 = new SearchFragment();

                return tab1;
            case 1:
                RecommendFragment tab2 = new RecommendFragment();
                Bundle args = new Bundle();
                args.putInt("userID", userID);
                tab2.setArguments((args));
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}