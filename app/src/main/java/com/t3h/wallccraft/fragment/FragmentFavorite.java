package com.t3h.wallccraft.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.t3h.wallccraft.R;
import com.t3h.wallccraft.activity.DetailActivity;
import com.t3h.wallccraft.activity.ImageActivity;
import com.t3h.wallccraft.adapter.ListImageAdapter;
import com.t3h.wallccraft.dao.AppDatabase;
import com.t3h.wallccraft.model.ListImage;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentFavorite extends Fragment implements ListImageAdapter.ItemClickListener,SwipeRefreshLayout.OnRefreshListener {
    private ArrayList <ListImage> favoriteList;
    private ListImageAdapter adapter;
    @BindView(R.id.lv_favorite)
    RecyclerView getRecycleview;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressBarFavorite)
    ProgressBar progressBar;
//    @BindView(R.id.ln_favorite_empty)
//    LinearLayout layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_favorite,container,false);
        ButterKnife.bind(this,view);
        progressBar.setVisibility(View.VISIBLE);
        favoriteList=new ArrayList<>();
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
        favoriteList = (ArrayList<ListImage>) AppDatabase.getInstance(getContext()).getImagesDao().getAllFavorite();
        if (favoriteList != null && favoriteList.size()>0) {
            Log.e("dataFavorite:::", String.valueOf(favoriteList.size()));
            progressBar.setVisibility(View.GONE);
            adapter.setData(favoriteList);
            Log.e("favorite:::",favoriteList.toString());
            getRecycleview.setVisibility(View.VISIBLE);
//            layout.setVisibility(View.GONE);

        }
        progressBar.setVisibility(View.GONE);
        getRecycleview.setVisibility(View.GONE);
//        layout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorTab);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getContext().getResources().getColor(R.color.colorTabSelect));

    }

    @Override
    public void onClicked(int position) {
        Intent intent=new Intent(getContext(), ImageActivity.class);
        intent.putExtra("data",favoriteList);
        intent.putExtra("pos",position);
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
