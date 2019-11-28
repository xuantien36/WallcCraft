package com.t3h.wallccraft.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.t3h.wallccraft.R;
import com.t3h.wallccraft.fragment.FragmentNewDoubleWallpaper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivitySubscription extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.tv_subscription)
    TextView tvSubscription;
    @BindView(R.id.title)
    TextView tvTitle;
    @BindView(R.id.back)
    ImageView imBack;
    @BindView(R.id.reload)
    ImageView imReload;
    @BindView(R.id.progressbarSub)
    ProgressBar progressBar;

    private CallBackLock callBackLock;

    public void setOnCallBackLock(CallBackLock callBackLock) {
        this.callBackLock = callBackLock;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvSubscription.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        String title = intent.getStringExtra("subscription");
        tvTitle.setText(title);
        imBack.setOnClickListener(this);
        imReload.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.reload:
                progressBar.setVisibility(View.VISIBLE);

                break;
        }

    }

    public interface CallBackLock {
        void onCallBack();
    }

}
