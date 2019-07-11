package com.t3h.testclient;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ChatFunAdapter extends FragmentPagerAdapter {
    public ChatFunAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0){
            return new FriendFragment();
        }
        return new OtherFriendFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return "Friend";
        }
        return "Other Friend";
    }

    @Override
    public int getCount() {
        return 2;
    }
}
