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

public class FragmentRatingDoubleWallpaper extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<Phone>arr;
    private DoubleWallAdapter adapter;
    @BindView(R.id.lv_rating_dowble_wall)
    RecyclerView recyclerView;

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
    }

    private void initView() {
        adapter=new DoubleWallAdapter(getActivity());
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onRefresh() {

    }
}
