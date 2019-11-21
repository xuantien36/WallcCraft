package com.t3h.wallccraft.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.t3h.wallccraft.R;
import com.t3h.wallccraft.adapter.PageDoubleWallpaperAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentDoubleWallPaper extends Fragment {
    private PageDoubleWallpaperAdapter wallAdapter;
    private FragmentNewDoubleWallpaper newDoubleWallpaper = new FragmentNewDoubleWallpaper();
    private FragmentRatingDoubleWallpaper ratingDoubleWallpaper = new FragmentRatingDoubleWallpaper();
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager viewPager;

    public static FragmentDoubleWallPaper newInstance(int someInt) {
        FragmentDoubleWallPaper myFragment = new FragmentDoubleWallPaper();
        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_double_wallpaper, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();

    }

    private void initData() {

    }

    private void initView() {
        Fragment[] frm = {newDoubleWallpaper, ratingDoubleWallpaper};
        wallAdapter = new PageDoubleWallpaperAdapter(getActivity().getSupportFragmentManager(),frm);
        viewPager.setAdapter(wallAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
