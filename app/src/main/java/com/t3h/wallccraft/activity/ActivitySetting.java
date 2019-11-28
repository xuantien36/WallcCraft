package com.t3h.wallccraft.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.t3h.wallccraft.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivitySetting extends AppCompatActivity {
    @BindView(R.id.swich_one)
    Switch switchOne;
    @BindView(R.id.swich_two)
    Switch switchTwo;
    @BindView(R.id.swich_three)
    Switch switchThree;
    private SharedPreferences sharedPreferences;
    @BindView(R.id.back)
    ImageView imBack;
    @BindView(R.id.title)
    TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
        sharedPreferences = getSharedPreferences("SaveDataLogin", Context.MODE_PRIVATE);
        switchOne.setChecked(sharedPreferences.getBoolean("checked", false));
        switchTwo.setChecked(sharedPreferences.getBoolean("checked2", false));
        switchThree.setChecked(sharedPreferences.getBoolean("checked3", false));
        Intent intent=getIntent();
        String title=intent.getStringExtra("setting");
        tvTitle.setText(title);

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
                SharedPreferences.Editor editor = sharedPreferences.edit();
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
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("checked3");
                editor.commit();
            }
        });
        imBack.setOnClickListener(view -> finish());
    }
}






