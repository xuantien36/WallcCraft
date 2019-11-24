package com.t3h.wallccraft.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageAllAdapter extends FragmentStatePagerAdapter {

    private Fragment[] frmData;

    public PageAllAdapter(@NonNull FragmentManager fm,Fragment[] frmData) {
        super(fm);
        this.frmData=frmData;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return frmData[position];
    }

    @Override
    public int getCount() {
        return frmData == null ? 0 : frmData.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "New";
            case 1:
                return "Rating";
            case 2:
                return "Exclusive";
            case 3:
                return "Hits";
            case 4:
                return "Random";
            case 5:
                return "Stream";

        }
        return getPageTitle(position);

    }
}

