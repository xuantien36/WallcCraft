package com.t3h.wallccraft.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;


import retrofit2.Callback;
import retrofit2.Response;

public class Fragment60FavoriteRating extends Fragment implements ListImageAdapter.ItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<ListImage> ratingList;
    private ListImageAdapter adapter;
    @BindView(R.id.lv_im_rating_60Favorite)
    RecyclerView recyclerView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    private int pos;
    @BindView(R.id.tv_test)
    TextView tvTest;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating_60favoirte, container, false);
        ButterKnife.bind(this, view);
        ratingList = new ArrayList<>();
        adapter = new ListImageAdapter(getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnListener(this);
//        callApi(getArguments().getInt("someInt"));
        return view;
    }

    public static Fragment60FavoriteRating newInstance(int idRating) {
        Fragment60FavoriteRating myFragment = new Fragment60FavoriteRating();
        Bundle args = new Bundle();
        args.putInt("idRatingFavorite", idRating);
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
        Log.e("pos", String.valueOf(pos));
        super.onResume();
    }

    @Override
    public void onClicked(int position) {
        Intent intent = new Intent(getContext(), ImageActivity.class);
        intent.putExtra("data", ratingList);
        startActivity(intent);

    }
    @Override
    public void onLongClicked(int position) {

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);

    }
    public void callApi(int id) {
        pos = id;
        ApiBuilder.getInstance().getAlbumDetail(String.valueOf(id)).enqueue(new Callback<ListImageRespone>() {
            @Override
            public void onResponse(Call<ListImageRespone> call, Response<ListImageRespone> response) {
                ArrayList<ListImage> data = (ArrayList<ListImage>) response.body().getListImage();
                if (data != null && data.size()>0) {
                    adapter.setData(data);
                    ratingList.clear();
                    ratingList.addAll(data);
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
