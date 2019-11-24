package com.t3h.wallccraft.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.t3h.wallccraft.R;
import com.t3h.wallccraft.activity.DetailActivity;
import com.t3h.wallccraft.adapter.ListImageAdapter;
import com.t3h.wallccraft.adapter.ListImageHistoryAdapter;
import com.t3h.wallccraft.dao.AppDatabase;
import com.t3h.wallccraft.model.ListImage;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentHistory extends Fragment implements ListImageAdapter.ItemClickListener, ListImageHistoryAdapter.ItemClickListener {
    private ArrayList<ListImage>data;
    private ListImageHistoryAdapter adapter;
    @BindView(R.id.lv_history)
    RecyclerView getRecycleview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_history,container,false);
        ButterKnife.bind(this,view);

        data=new ArrayList<>();
        adapter=new ListImageHistoryAdapter(getContext());
        getRecycleview.setAdapter(adapter);
        adapter.setOnListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        data = (ArrayList<ListImage>) AppDatabase.getInstance(getContext()).getImagesDao().getAllHistoty();
        Log.e("abcd",data.toString());
        if (data != null) {
            adapter.setData(data);
            Log.e("data", String.valueOf(data.size()));

        }
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
}
