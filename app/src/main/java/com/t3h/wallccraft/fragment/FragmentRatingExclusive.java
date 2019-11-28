package com.t3h.wallccraft.fragment;

import android.content.Intent;
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
import com.t3h.wallccraft.activity.DetailActivity;
import com.t3h.wallccraft.activity.ImageActivity;
import com.t3h.wallccraft.adapter.ListImageAdapter;
import com.t3h.wallccraft.model.ListImage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentRatingExclusive extends Fragment implements ListImageAdapter.ItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<ListImage> arrayList;
    private ListImageAdapter imageAdapter;
    @BindView(R.id.lv_rating_exclusive)
    RecyclerView recyclerView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    public static FragmentRatingExclusive newInstance(int someInt) {
        FragmentRatingExclusive myFragment = new FragmentRatingExclusive();
        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating_exclusive, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();

    }

    private void initData() {
        arrayList = new ArrayList<>();
        arrayList.add(new ListImage(1, "Anh 12", "https://tse3.mm.bing.net/th?id=OIP.RnL-xNIsV2KLX-vY_ZREggHaNK&pid=Api&P=0&w=300&h=300"));
        arrayList.add(new ListImage(2, "Anh 13", "https://tse3.mm.bing.net/th?id=OIF.uFgRBR8qK5nc8pI%2bbgKXog&pid=Api&P=0&w=300&h=300"));
        arrayList.add(new ListImage(3, "Anh 14", "https://tse3.mm.bing.net/th?id=OIP.RnL-xNIsV2KLX-vY_ZREggHaNK&pid=Api&P=0&w=300&h=300"));
        arrayList.add(new ListImage(4, "Anh 15", "http://4.bp.blogspot.com/-Mz3_1zkQ85k/U98iFr_DJwI/AAAAAAAAArs/PtJhgVM5In0/s000/anh-dep-thien-nhien-thang-canh-thien-nhien-dep-ngo-ngang-o-nhat-ban-6.jpg"));
        arrayList.add(new ListImage(5, "Anh 16", "https://tse3.mm.bing.net/th?id=OIP.RnL-xNIsV2KLX-vY_ZREggHaNK&pid=Api&P=0&w=300&h=300"));
        arrayList.add(new ListImage(6, "Anh 17", "https://tse3.mm.bing.net/th?id=OIF.uFgRBR8qK5nc8pI%2bbgKXog&pid=Api&P=0&w=300&h=300"));
        arrayList.add(new ListImage(7, "Anh 18", "https://tse3.mm.bing.net/th?id=OIP.RnL-xNIsV2KLX-vY_ZREggHaNK&pid=Api&P=0&w=300&h=300"));
        arrayList.add(new ListImage(8, "Anh 19", "http://4.bp.blogspot.com/-Mz3_1zkQ85k/U98iFr_DJwI/AAAAAAAAArs/PtJhgVM5In0/s000/anh-dep-thien-nhien-thang-canh-thien-nhien-dep-ngo-ngang-o-nhat-ban-6.jpg"));
        arrayList.add(new ListImage(9, "Anh 20", "https://tse3.mm.bing.net/th?id=OIP.RnL-xNIsV2KLX-vY_ZREggHaNK&pid=Api&P=0&w=300&h=300"));
        arrayList.add(new ListImage(10, "Anh 21", "https://tse3.mm.bing.net/th?id=OIF.uFgRBR8qK5nc8pI%2bbgKXog&pid=Api&P=0&w=300&h=300"));
        arrayList.add(new ListImage(11, "Anh 22", "https://tse3.mm.bing.net/th?id=OIP.RnL-xNIsV2KLX-vY_ZREggHaNK&pid=Api&P=0&w=300&h=300"));
        arrayList.add(new ListImage(12, "Anh 23", "https://tse3.mm.bing.net/th?id=OIF.uFgRBR8qK5nc8pI%2bbgKXog&pid=Api&P=0&w=300&h=300"));
        imageAdapter.setData(arrayList);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void initView() {
        imageAdapter = new ListImageAdapter(getContext());
        imageAdapter.setOnListener(this);
        recyclerView.setAdapter(imageAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorTab);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getContext().getResources().getColor(R.color.colorTabSelect));
    }

    @Override
    public void onClicked(int position) {
        Intent intent = new Intent(getContext(), ImageActivity.class);
        intent.putExtra("data", arrayList);
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
