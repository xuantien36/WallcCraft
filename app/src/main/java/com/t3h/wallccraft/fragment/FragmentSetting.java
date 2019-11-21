package com.t3h.wallccraft.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.t3h.wallccraft.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentSetting extends Fragment {
    @BindView(R.id.swich_one)
    Switch switchOne;
    @BindView(R.id.swich_two)
    Switch switchTwo;
    @BindView(R.id.swich_three)
    Switch switchThree;
    @BindView(R.id.tv_Data)
    TextView tvData;
    @BindView(R.id.tv_Setting)
    TextView tvSetting;
    @BindView(R.id.tv_Notification)
    TextView tvNotification;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        sharedPreferences =getActivity(). getSharedPreferences("SaveDataLogin", Context.MODE_PRIVATE);
        switchOne.setChecked(sharedPreferences.getBoolean("checked", false));
        switchTwo.setChecked(sharedPreferences.getBoolean("checked2", false));
        switchThree.setChecked(sharedPreferences.getBoolean("checked3", false));
    }
    private void initView() {
        switchOne.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("checked", true);
                    editor.commit();

                } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("checked");
                editor.commit();
            }

        });
        switchTwo.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("checked2", true);
                    editor.commit();

                } else {
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.remove("checked2");
                    editor.commit();
                }
        });
        switchThree.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("checked3", true);
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.remove("checked3");
                    editor.commit();
                }
        });
    }
}





