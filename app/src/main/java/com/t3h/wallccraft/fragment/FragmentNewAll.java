package com.t3h.wallccraft.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.t3h.wallccraft.R;
import com.t3h.wallccraft.activity.DetailActivity;
import com.t3h.wallccraft.activity.MainActivity;
import com.t3h.wallccraft.adapter.ListImageAdapter;
import com.t3h.wallccraft.apialbum.ApiBuilder;
import com.t3h.wallccraft.model.ListAlbumRespone;
import com.t3h.wallccraft.model.ListImage;
import com.t3h.wallccraft.model.ListImageRespone;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentNewAll extends Fragment implements ListImageAdapter.ItemClickListener, SwipeRefreshLayout.OnRefreshListener, MainActivity.CallBackSearch {
    private ArrayList<ListImage> data;
    private ListImageAdapter adapter;
    @BindView(R.id.lv_im_new)
    RecyclerView recyclerView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;


    public static FragmentNewAll newInstance() {
        FragmentNewAll myFragment = new FragmentNewAll();
        Bundle args = new Bundle();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_all, container, false);
        ButterKnife.bind(this, view);
//        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        data = new ArrayList<>();
        adapter = new ListImageAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        adapter.setOnListener(this);
        MainActivity.setCallBackSearch(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorTab);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getContext().getResources().getColor(R.color.colorTabSelect));
    }

    public void callApi(int id) {
//        initView();
        ApiBuilder.getInstance().getAlbumDetail(String.valueOf(id)).enqueue(new Callback<ListImageRespone>() {
            @Override
            public void onResponse(Call<ListImageRespone> call, Response<ListImageRespone> response) {
                List<ListImage> listImage = response.body().getListImage();
                if (listImage != null) {
                    if (adapter != null) {
                        adapter.setData((ArrayList<ListImage>) listImage);
                        data.addAll(listImage);
                    }

                }
            }

            @Override
            public void onFailure(Call<ListImageRespone> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClicked(int position) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("data", data.get(position));
        startActivity(intent);
    }

    @Override
    public void onLongClicked(int position) {

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onQuerySearch(String text) {

        ApiBuilder.getInstance().getAlbumSearch(text).enqueue(new Callback<ListAlbumRespone>() {

            @Override
            public void onResponse(Call<ListAlbumRespone> call, Response<ListAlbumRespone> response) {
                List<ListImage> listImage = response.body().getListImage();
                if (listImage != null) {
                    ListImage[] arr = new ListImage[listImage.size()];
                    listImage.toArray(arr);
                    adapter.setData((ArrayList<ListImage>) listImage);


                } else {
                    Toast.makeText(getContext(), "not found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ListAlbumRespone> call, Throwable t) {

            }
        });
    }
}



