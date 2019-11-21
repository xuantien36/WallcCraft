package com.t3h.wallccraft.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.t3h.wallccraft.R;
import com.t3h.wallccraft.activity.DetailActivity;
import com.t3h.wallccraft.adapter.ListImageAdapter;
import com.t3h.wallccraft.dao.AppDatabase;
import com.t3h.wallccraft.model.ListImage;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentFavorite extends Fragment implements ListImageAdapter.ItemClickListener,SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<ListImage>data;
    private ListImageAdapter adapter;
    @BindView(R.id.lv_favorite)
    RecyclerView getRecycleview;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler;
    @BindView(R.id.progressBarFavorite)
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_favorite,container,false);
        ButterKnife.bind(this,view);
        progressBar.setVisibility(View.VISIBLE);
        data=new ArrayList<>();
        adapter=new ListImageAdapter(getContext());
        getRecycleview.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
        adapter.setOnListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        data = (ArrayList<ListImage>) AppDatabase.getInstance(getContext()).getImagesDao().getAllFavorite();
        if (data != null) {
            progressBar.setVisibility(View.GONE);
            adapter.setData(data);

        }
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorTab);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getContext().getResources().getColor(R.color.colorTabSelect));

    }

    @Override
    public void onClicked(int position) {
        Intent intent=new Intent(getContext(), DetailActivity.class);
        intent.putExtra("data",data.get(position));
        startActivity(intent);

    }

    @Override
    public void onLongClicked(int position) {

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);

    }
}
