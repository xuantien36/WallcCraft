package com.t3h.wallccraft.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.t3h.wallccraft.R;
import com.t3h.wallccraft.activity.DetailActivity;
import com.t3h.wallccraft.activity.ImageActivity;
import com.t3h.wallccraft.adapter.ListImageAdapter;
import com.t3h.wallccraft.apialbum.ApiBuilder;
import com.t3h.wallccraft.model.ListImage;
import com.t3h.wallccraft.model.ListImageRespone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentRandomAll extends Fragment implements ListImageAdapter.ItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<ListImage> data;
    private ListImageAdapter adapter;
    @BindView(R.id.lv_im_random_all)
    RecyclerView recyclerView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    public static FragmentRandomAll newInstance(int id) {
        FragmentRandomAll myFragment = new FragmentRandomAll();
        Bundle args = new Bundle();
        args.putInt("someIntRandomAll", id);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_random_all, container, false);
        ButterKnife.bind(this, view);
        data=new ArrayList<>();
        adapter = new ListImageAdapter(getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }
    private void initView() {
        adapter.setOnListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorTab);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getContext().getResources().getColor(R.color.colorTabSelect));


    }
    public void callApi(int id){
        ApiBuilder.getInstance().getAlbumDetail(String.valueOf(id)).enqueue(new Callback<ListImageRespone>() {
            @Override
            public void onResponse(Call<ListImageRespone> call, Response<ListImageRespone> response) {
                List<ListImage> listImage = response.body().getListImage();
                if (listImage!=null){
                    adapter.setData((ArrayList<ListImage>) listImage);
                    data.clear();
                    data.addAll(listImage);
                }
            }

            @Override
            public void onFailure(Call<ListImageRespone> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClicked(int position) {
        Intent intent = new Intent(getContext(), ImageActivity.class);
        intent.putExtra("data", data);
        intent.putExtra("pos",position);
        startActivity(intent);

    }

    @Override
    public void onLongClicked(int position) {

    }

    @Override
    public void onRefresh() {
        shuffle();
        swipeRefreshLayout.setRefreshing(false);

    }

    public void shuffle() {
        Collections.shuffle(data, new Random(System.currentTimeMillis()));
        adapter = new ListImageAdapter(getContext());
        recyclerView.setAdapter(adapter);

    }
}
