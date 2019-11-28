package com.t3h.wallccraft.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.t3h.wallccraft.R;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityWallpaperCraftInstaller extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.im_set_wallpaper)
    ImageView imSetWallpaper;
    @BindView(R.id.im_set_clock)
    ImageView imSetClock;
    @BindView(R.id.im_set_home)
    ImageView imSetHome;
    @BindView(R.id.im_set_clock_home)
    ImageView imSetClockHome;
    String path;
    String url;
    public static final String TAG=ActivityWallpaperCraftInstaller.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpapaer_craft_installer);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        path = intent.getStringExtra("setWallpaper");
        Log.e(TAG,path);
        Glide.with(this)
                .load(url)
                .error(R.drawable.ic_launcher_background).into(imSetWallpaper);

        initView();
    }

    private void initView() {
        imSetClock.setOnClickListener(this);
        imSetHome.setOnClickListener(this);
        imSetClockHome.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_set_clock:
                setWallpaperImage();
                finish();
                break;
            case R.id.im_set_home:
                setWallpaperImage();
                finish();
                break;
            case R.id.im_set_clock_home:
                setWallpaperImage();
                finish();
                break;
        }
    }

    public void setWallpaperImage() {
        runOnUiThread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                setWallpaper();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setWallpaper() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        WallpaperManager wpm = WallpaperManager.getInstance(this);
        try {
            wpm.setBitmap(bitmap);
            showNotification(this, "Wallpaper has been successfully set.", "");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void showNotification(Context context, String title, String body) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body);
        Intent intent = new Intent(context, DetailActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());


    }
}
