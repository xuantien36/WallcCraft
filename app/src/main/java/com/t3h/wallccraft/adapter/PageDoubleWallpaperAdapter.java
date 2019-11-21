package com.t3h.wallccraft.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageDoubleWallpaperAdapter extends FragmentStatePagerAdapter {

    private Fragment[] frmData;

//    public void setFrgyt
//    mData(Fragment[] frmData) {
//        this.frmData = frmData;
//        notifyDataSetChanged();
//    }

    public PageDoubleWallpaperAdapter(@NonNull FragmentManager fm, Fragment[] frmData) {
        super(fm);
        this.frmData = frmData;
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
        }
        return getPageTitle(position);

    }
}

