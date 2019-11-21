package com.t3h.wallccraft.newfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.t3h.wallccraft.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryFragment extends Fragment {
    @BindView(R.id.lv_tab)
    RecyclerView recyclerView;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categori_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
