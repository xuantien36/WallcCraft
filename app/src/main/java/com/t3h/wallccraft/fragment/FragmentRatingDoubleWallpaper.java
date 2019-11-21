package com.t3h.wallccraft.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.t3h.wallccraft.R;
import com.t3h.wallccraft.adapter.DoubleWallAdapter;
import com.t3h.wallccraft.model.Phone;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentRatingDoubleWallpaper extends Fragment implements DoubleWallAdapter.ItemClickListener,SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<Phone>arr;
    private DoubleWallAdapter adapter;
    @BindView(R.id.lv_rating_dowble_wall)
    RecyclerView recyclerView;
//    @BindView(R.id.swipe)
//    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_rating_double_wallpaper,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();

    }

    private void initData() {
        arr=new ArrayList<>();
        arr.add(new Phone(R.drawable.phoneop,R.drawable.ic_lock_black_24dp));
        arr.add(new Phone(R.drawable.phoneop,R.drawable.ic_lock_black_24dp));
        arr.add(new Phone(R.drawable.phoneop,R.drawable.ic_lock_black_24dp));
        arr.add(new Phone(R.drawable.phoneop,R.drawable.ic_lock_black_24dp));
        arr.add(new Phone(R.drawable.phoneop,R.drawable.ic_lock_black_24dp));
        arr.add(new Phone(R.drawable.phoneop,R.drawable.ic_lock_black_24dp));
        arr.add(new Phone(R.drawable.phoneop,R.drawable.ic_lock_black_24dp));
        arr.add(new Phone(R.drawable.phoneop,R.drawable.ic_lock_black_24dp));
        arr.add(new Phone(R.drawable.phoneop,R.drawable.ic_lock_black_24dp));
        arr.add(new Phone(R.drawable.phoneop,R.drawable.ic_lock_black_24dp));
        arr.add(new Phone(R.drawable.phoneop,R.drawable.ic_lock_black_24dp));
        arr.add(new Phone(R.drawable.phoneop,R.drawable.ic_lock_black_24dp));
        adapter.setData(arr);
//        swipeRefreshLayout.setRefreshing(false);
    }

    private void initView() {
        adapter=new DoubleWallAdapter(getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnListener(this);
//        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorTab);
//        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getContext().getResources().getColor(R.color.colorTabSelect));

    }

    @Override
    public void onClicked(int position) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,new FragmentSubscription()).commit();

    }

    @Override
    public void onLongClicked(int position) {

    }

    @Override
    public void onRefresh() {
//        swipeRefreshLayout.setRefreshing(false);


    }
}
