package com.t3h.wallccraft.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.t3h.wallccraft.EvenPost;
import com.t3h.wallccraft.R;
import com.t3h.wallccraft.activity.DetailActivity;
import com.t3h.wallccraft.activity.MainActivity;
import com.t3h.wallccraft.adapter.ListImageAdapter;
import com.t3h.wallccraft.apialbum.ApiBuilder;
import com.t3h.wallccraft.model.ListImage;
import com.t3h.wallccraft.model.ListImageRespone;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentNewAll extends Fragment implements ListImageAdapter.ItemClickListener, SwipeRefreshLayout.OnRefreshListener, MainActivity.CallBackSearch, SearchView.OnQueryTextListener {
    private ArrayList<ListImage> data;
    private ListImageAdapter adapter;
    @BindView(R.id.lv_im_new)
    RecyclerView recyclerView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchView;
    private ProgressDialog progressDialog;
    private String name;

    public static FragmentNewAll newInstance(int id) {
        FragmentNewAll myFragment = new FragmentNewAll();
        Bundle args = new Bundle();
        args.putInt("someIntNewAll", id);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_all, container, false);
        ButterKnife.bind(this, view);

//        callApi(getArguments().getInt("someIntNewAll"));
//        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.search);
        searchView = (SearchView) item.getActionView();
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);

    }


    private void initView() {
        data = new ArrayList<>();
        adapter = new ListImageAdapter(getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnListener(this);
        MainActivity.setCallBackSearch(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorTab);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getContext().getResources().getColor(R.color.colorTabSelect));
    }

    public void callApi(int id) {
        ApiBuilder.getInstance().getAlbumDetail(String.valueOf(id)).enqueue(new Callback<ListImageRespone>() {
            @Override
            public void onResponse(Call<ListImageRespone> call, Response<ListImageRespone> response) {
                List<ListImage> listImage = response.body().getListImage();
                if (listImage != null) {
                    adapter.setData((ArrayList<ListImage>) listImage);
                    data.addAll(listImage);
                }
            }

            @Override
            public void onFailure(Call<ListImageRespone> call, Throwable t) {

            }
        });

    }

    public void callApiSearch(String name) {
        ApiBuilder.getInstance().getAlbumSearch(name).enqueue(new Callback<ListImageRespone>() {
            @Override
            public void onResponse(Call<ListImageRespone> call, Response<ListImageRespone> response) {
                List<ListImage> listImage = response.body().getListImage();
                if (listImage != null) {
                    adapter.setData((ArrayList<ListImage>) listImage);
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
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading........");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        ApiBuilder.getInstance().getAlbumSearch(text).enqueue(new Callback<ListImageRespone>() {

            @Override
            public void onResponse(Call<ListImageRespone> call, Response<ListImageRespone> response) {
                List<ListImage> listImage = response.body().getListImage();
                if (listImage!=null) {
                    ListImage[] arr = new ListImage[listImage.size()];
                    listImage.toArray(arr);
                    progressDialog.dismiss();
                    adapter.setData((ArrayList<ListImage>) listImage);
                } else {
                    Toast.makeText(getContext(), "not found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ListImageRespone> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        progressDialog =new ProgressDialog(getContext());
        progressDialog.setMessage("Loading........");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        ApiBuilder.getInstance().getAlbumSearch(query).enqueue(new Callback<ListImageRespone>() {
            @Override
            public void onResponse(Call<ListImageRespone> call, Response<ListImageRespone> response) {
                List<ListImage> images = response.body().getListImage();
                if (!images.isEmpty()) {
                    progressDialog.dismiss();
                    adapter.setData((ArrayList<ListImage>) images);
                    Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Nothing to show!", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ListImageRespone> call, Throwable t) {

            }
        });

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
//    public void onEvent(EvenPost evenPost) {
////        name = evenPost.getName();
//        EventBus.getDefault().removeStickyEvent(evenPost);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        EventBus.getDefault().unregister(this);
//    }

}

