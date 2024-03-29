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
import com.t3h.wallccraft.adapter.PageAllAdapter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentAll extends Fragment implements TabLayout.BaseOnTabSelectedListener {
    private PageAllAdapter wallAdapter;
    private FragmentNewAll fragmentNew = new FragmentNewAll();
    private FragmentRatingAll fragmentRating = new FragmentRatingAll();
    private FragmentAllExclusive fragmentExClusive = new FragmentAllExclusive();
    private FragmentHitsAll fragmentHits = new FragmentHitsAll();
    private FragmentRandomAll fragmentRandom = new FragmentRandomAll();
    private FragmentStreamAll fragmentStream = new FragmentStreamAll();
    @BindView(R.id.tablayoutAll)
    TabLayout tabLayout;
    @BindView(R.id.pagerAll)
    ViewPager viewPager;
    int id;
    int position = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        EventBus.getDefault().register(this);
        return view;
    }

    public static FragmentAll newInstance(int id) {
        FragmentAll myFragment = new FragmentAll();
        Bundle args = new Bundle();
        args.putInt("idBundle", id);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        Fragment[] frm = {fragmentNew, fragmentRating, fragmentExClusive, fragmentHits, fragmentRandom, fragmentStream};
        wallAdapter = new PageAllAdapter(getChildFragmentManager(), frm);
        viewPager.setAdapter(wallAdapter);
        viewPager.setCurrentItem(0);
        fragmentNew.callApi(getArguments().getInt("idBundle"));
        tabLayout.addOnTabSelectedListener(this);
        viewPager.setOffscreenPageLimit(6);
        tabLayout.setupWithViewPager(viewPager);

        Log.e("testtttt::", String.valueOf(getArguments().getInt("idBundle")));
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        fragmentNew.callApi(getArguments().getInt("idBundle"));
        fragmentRating.callApi(getArguments().getInt("idBundle"));
        fragmentExClusive.callApi(getArguments().getInt("idBundle"));
        fragmentHits.callApi(getArguments().getInt("idBundle"));
        fragmentRandom.callApi(getArguments().getInt("idBundle"));
        fragmentStream.callApi(getArguments().getInt("idBundle"));

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(EvenPost evenPost) {
        Log.e("All", String.valueOf(evenPost.getId()));
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
        Log.e("All","alll");
    }
}

