package com.t3h.wallccraft.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.t3h.wallccraft.adapter.ListImageAdapter;
import com.t3h.wallccraft.apialbum.ApiBuilder;
import com.t3h.wallccraft.model.ListImage;
import com.t3h.wallccraft.model.ListImageRespone;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHitsAll extends Fragment implements ListImageAdapter.ItemClickListener,SwipeRefreshLayout.OnRefreshListener{
    private ArrayList<ListImage> data;
    private ListImageAdapter adapter;
    @BindView(R.id.lv_im_hits)
    RecyclerView recyclerView;
    @BindView(R.id.tv_clock)
    TextView textView;
    @BindView(R.id.swipe_hits)
    SwipeRefreshLayout swipeRefreshLayout;

    public static FragmentHitsAll newInstance(int id) {
        FragmentHitsAll myFragment = new FragmentHitsAll();
        Bundle args = new Bundle();
        args.putInt("someIntAll", id);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hits, container, false);
        ButterKnife.bind(this, view);
        data=new ArrayList<>();
//        callApi(getArguments().getInt("someIntAll"));
        return view;
    }
    public void callApi(int id){
        ApiBuilder.getInstance().getAlbumDetail(String.valueOf(id)).enqueue(new Callback<ListImageRespone>() {
            @Override
            public void onResponse(Call<ListImageRespone> call, Response<ListImageRespone> response) {
                List<ListImage> listImage = response.body().getListImage();
                if (listImage!=null){
                    adapter.setData((ArrayList<ListImage>) listImage);
                }
            }

            @Override
            public void onFailure(Call<ListImageRespone> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }
    private void initView() {
        adapter = new ListImageAdapter(getContext());
        recyclerView.setAdapter(adapter);
        CountDownTimer timer = new CountDownTimer(20000000, 1000) {
            @Override
            public void onTick(long l) {
                long durationSeconds = l / 1000;
                textView.setText(String.format("%02d:%02d:%02d", durationSeconds / 3600,
                        (durationSeconds % 3600) / 60, (durationSeconds % 60)));
            }
            @Override
            public void onFinish() {

            }
        };
        timer.start();
        adapter.setOnListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorTab);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getContext().getResources().getColor(R.color.colorTabSelect));
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
}
