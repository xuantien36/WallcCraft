package com.t3h.wallccraft.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment60FavoriteRandom extends Fragment implements ListImageAdapter.ItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<ListImage> randomList;
    private ListImageAdapter adapter;
    @BindView(R.id.lv_im_random_60Favorite)
    RecyclerView recyclerView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    private int pos;
    @BindView(R.id.tv_test)
    TextView tvTest;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_random_60favorite, container, false);
        ButterKnife.bind(this, view);
        randomList = new ArrayList<>();
        adapter = new ListImageAdapter(getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnListener(this);
//        callApi(getArguments().getInt(""));
        return view;
    }

    public static Fragment60FavoriteRandom newInstance(int idRandom) {
        Fragment60FavoriteRandom myFragment = new Fragment60FavoriteRandom();
        Bundle args = new Bundle();
        args.putInt("idRandomFavorite", idRandom);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorTab);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getContext().getResources().getColor(R.color.colorTabSelect));


    }

    @Override
    public void onResume() {
        callApi(pos);
        super.onResume();
    }

    @Override
    public void onClicked(int position) {
        Intent intent = new Intent(getContext(), ImageActivity.class);
        intent.putExtra("data",randomList);
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
        Collections.shuffle(randomList, new Random(System.currentTimeMillis()));
        adapter = new ListImageAdapter(getContext());
        recyclerView.setAdapter(adapter);

    }

    public void callApi(int id) {
        pos = id;
        ApiBuilder.getInstance().getAlbumDetail(String.valueOf(id)).enqueue(new Callback<ListImageRespone>() {
            @Override
            public void onResponse(Call<ListImageRespone> call, Response<ListImageRespone> response) {
                ArrayList<ListImage> data = (ArrayList<ListImage>) response.body().getListImage();
                if (data != null &&data.size()>0) {
                    adapter.setData(data);
                    randomList.clear();
                    randomList.addAll(data);
                    recyclerView.setVisibility(View.VISIBLE);
                    tvTest.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    tvTest.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onFailure(Call<ListImageRespone> call, Throwable t) {

            }
        });

    }
}
