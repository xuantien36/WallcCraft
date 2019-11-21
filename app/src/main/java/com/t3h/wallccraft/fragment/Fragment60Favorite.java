package com.t3h.wallccraft.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.t3h.wallccraft.EvenPost;
import com.t3h.wallccraft.R;
import com.t3h.wallccraft.adapter.Page60FavoriteAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment60Favorite extends Fragment {
    private Page60FavoriteAdapter wallAdapter;
    private Fragment60FavoriteNew fragmentNew = new Fragment60FavoriteNew();
    private Fragment60FavoriteRating fragmentRating = new Fragment60FavoriteRating();
    private Fragment60FavoriteRandom fragmentRandom = new Fragment60FavoriteRandom();

    @BindView(R.id.tablayoutAll)
    TabLayout tabLayout;
    @BindView(R.id.pagerAll)
    ViewPager viewPager;
    int id;
    int position = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_60favorite, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        return view;
    }

    public static Fragment60Favorite newInstance() {
        Fragment60Favorite myFragment = new Fragment60Favorite();
        Bundle args = new Bundle();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView() {
        Fragment[] frm = {fragmentNew, fragmentRating, fragmentRandom};
        wallAdapter = new Page60FavoriteAdapter(getActivity().getSupportFragmentManager(), frm);
        viewPager.setAdapter(wallAdapter);
        viewPager.setCurrentItem(0);
        fragmentNew.callApi(id);
        viewPager.setOffscreenPageLimit(3);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                fragmentNew.callApi(id);
                fragmentRating.callApi(id);
                fragmentRandom.callApi(id);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.setupWithViewPager(viewPager);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(EvenPost evenPost) {
        Log.e("60Favorite", String.valueOf(evenPost.getId()));
        id = evenPost.getId();
        EventBus.getDefault().removeStickyEvent(evenPost);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}

