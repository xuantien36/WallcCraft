package com.t3h.wallccraft.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.t3h.wallccraft.R;
import com.t3h.wallccraft.adapter.PageDoubleWallpaperAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentExclusive extends Fragment {
    private PageDoubleWallpaperAdapter wallAdapter;
    private FragmentNewExclusive newDoubleWallpaper = new FragmentNewExclusive();
    private FragmentRatingExclusive ratingDoubleWallpaper = new FragmentRatingExclusive();
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager viewPager;

    public static FragmentExclusive newInstance(int someInt) {
        FragmentExclusive myFragment = new FragmentExclusive();
        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exclusive, container, false);
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
        wallAdapter = new PageDoubleWallpaperAdapter(getActivity().getSupportFragmentManager(), frm);
        viewPager.setAdapter(wallAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
