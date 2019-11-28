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
import com.t3h.wallccraft.activity.ImageActivity;
import com.t3h.wallccraft.adapter.ListImageAdapter;
import com.t3h.wallccraft.model.ListImage;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentNewExclusive extends Fragment implements ListImageAdapter.ItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<ListImage> arr;
    private ListImageAdapter adapter;
    @BindView(R.id.lv_new_exclusive)
    RecyclerView recyclerView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler;
    @BindView(R.id.progressBarExclusive)
    ProgressBar progressBar;

    public static FragmentNewExclusive newInstance(int someInt) {
        FragmentNewExclusive myFragment = new FragmentNewExclusive();
        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_exclusive, container, false);
        ButterKnife.bind(this, view);
        progressBar.setVisibility(View.VISIBLE);
        handler=new Handler();
        handler.postDelayed(() -> progressBar.setVisibility(View.GONE),500);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();

    }

    private void initData() {
        arr = new ArrayList<>();
        arr.add(new ListImage(1,"Anh 1","https://tse3.mm.bing.net/th?id=OIP.RnL-xNIsV2KLX-vY_ZREggHaNK&pid=Api&P=0&w=300&h=300"));
        arr.add(new ListImage(2,"Anh 2","http://2.bp.blogspot.com/-5zjV7J3Nckc/UjrAFFOwT0I/AAAAAAAAFk0/_LEcRCf9QVA/s1600/anh+phong+canh+thien+nhien+tuyet+dep+(1).jpg"));
        arr.add(new ListImage(3,"Anh 3","https://tse2.mm.bing.net/th?id=OIP.kd5lRp1BejIDDeFJKBESgwHaLG&pid=Api&P=0&w=300&h=300"));
        arr.add(new ListImage(4,"Anh 4","https://tse3.mm.bing.net/th?id=OIP.RnL-xNIsV2KLX-vY_ZREggHaNK&pid=Api&P=0&w=300&h=300"));
        arr.add(new ListImage(5,"Anh 5","http://2.bp.blogspot.com/-5zjV7J3Nckc/UjrAFFOwT0I/AAAAAAAAFk0/_LEcRCf9QVA/s1600/anh+phong+canh+thien+nhien+tuyet+dep+(1).jpg"));
        arr.add(new ListImage(6,"Anh 6","https://tse2.mm.bing.net/th?id=OIP.kd5lRp1BejIDDeFJKBESgwHaLG&pid=Api&P=0&w=300&h=300"));
        arr.add(new ListImage(7,"Anh 7","http://2.bp.blogspot.com/-5zjV7J3Nckc/UjrAFFOwT0I/AAAAAAAAFk0/_LEcRCf9QVA/s1600/anh+phong+canh+thien+nhien+tuyet+dep+(1).jpg"));
        arr.add(new ListImage(8,"Anh 8","https://tse2.mm.bing.net/th?id=OIP.kd5lRp1BejIDDeFJKBESgwHaLG&pid=Api&P=0&w=300&h=300"));
        arr.add(new ListImage(9,"Anh 9","http://2.bp.blogspot.com/-5zjV7J3Nckc/UjrAFFOwT0I/AAAAAAAAFk0/_LEcRCf9QVA/s1600/anh+phong+canh+thien+nhien+tuyet+dep+(1).jpg"));
        arr.add(new ListImage(10,"Anh 10","https://tse2.mm.bing.net/th?id=OIP.kd5lRp1BejIDDeFJKBESgwHaLG&pid=Api&P=0&w=300&h=300"));
        arr.add(new ListImage(11,"Anh 11","http://2.bp.blogspot.com/-5zjV7J3Nckc/UjrAFFOwT0I/AAAAAAAAFk0/_LEcRCf9QVA/s1600/anh+phong+canh+thien+nhien+tuyet+dep+(1).jpg"));
        arr.add(new ListImage(12,"Anh 12","https://tse3.mm.bing.net/th?id=OIP.RnL-xNIsV2KLX-vY_ZREggHaNK&pid=Api&P=0&w=300&h=300"));
        adapter.setData(arr);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void initView() {
        adapter = new ListImageAdapter(getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorTab);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getContext().getResources().getColor(R.color.colorTabSelect));

    }
    @Override
    public void onClicked(int position) {
        Intent intent = new Intent(getContext(), ImageActivity.class);
        intent.putExtra("data", arr);
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
