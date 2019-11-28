package com.t3h.wallccraft.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.t3h.wallccraft.R;
import com.t3h.wallccraft.adapter.ImageAdapter;
import com.t3h.wallccraft.dao.AppDatabase;
import com.t3h.wallccraft.model.ListImage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private ImageAdapter adapter;
    private ArrayList<ListImage> arr;
    @BindView(R.id.btn_download_detail)
    Button btnDownload;
    @BindView(R.id.btn_set_detail)
    Button btnSet;
    private boolean isFavorite = false;
    private Handler handler;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private BottomSheetDialog bottomSheetDialog;
    private int REQUEST_CODE_IMAGE = 1;
    public static final String TAG = ImageActivity.class.getSimpleName();
    private String pathImage;
    private int poss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Intent intent = getIntent();
        arr = (ArrayList<ListImage>) intent.getSerializableExtra("data");
        poss=intent.getIntExtra("pos",0);
        progressBar.setVisibility(View.VISIBLE);
        handler = new Handler();
        handler.postDelayed(() -> progressBar.setVisibility(View.GONE), 500);
        adapter = new ImageAdapter(this);
        adapter.setDataList(arr);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(poss);
        btnDownload.setOnClickListener(this);
        btnSet.setOnClickListener(this);
        viewPager.setOnPageChangeListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image_detail, menu);
        MenuItem item = menu.findItem(R.id.favorite_detail);
        ListImage im = AppDatabase.getInstance(getApplicationContext()).getImagesDao().getImageById((arr.get(poss).getId()));
        if (im != null) {
            item.setIcon(im.isFavorite() ? R.drawable.ic_grade_black_24dp : R.drawable.ic_star_border_black_24dp);
        } else {
            item.setIcon(R.drawable.ic_star_border_black_24dp);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gallery:
                Intent intent = new Intent(this, EditImageActivity.class);
                intent.putExtra("filter", arr.get(poss).getThumbUrl());
                Log.e(TAG, arr.get(poss).getThumbUrl());
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
                break;
            case R.id.favorite_detail:
                selectFavorite(item);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    public void selectFavorite(MenuItem item) {
        isFavorite = !arr.get(poss).isFavorite();
        item.setIcon(isFavorite ? R.drawable.ic_grade_black_24dp : R.drawable.ic_star_border_black_24dp);
        arr.get(poss).setFavorite(isFavorite);
        if (isFavorite) {
            AppDatabase.getInstance(getApplicationContext()).getImagesDao().insert(arr.get(poss));
            Log.e("insert:::", arr.get(poss).toString());
        } else {
            AppDatabase.getInstance(getApplicationContext()).getImagesDao().deleteFavorite(arr.get(poss));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            pathImage = data.getStringExtra("image");
            Log.e(TAG, pathImage);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_download_detail:
                downloadImage();
                break;
            case R.id.btn_set_detail:
                setBackgroundImage();
                break;
            case R.id.ln_Portrait_download:
                DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(arr.get(poss).getThumbUrl());
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle("Download complete");
                request.setDescription("Downloading");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadmanager.enqueue(request);
                bottomSheetDialog.dismiss();
                break;
            case R.id.ln_Landscape_download:
                DownloadManager downloadLandscape = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri ui = Uri.parse(arr.get(poss).getThumbUrl());
                DownloadManager.Request rqLandscape = new DownloadManager.Request(ui);
                rqLandscape.setTitle("Download complete");
                rqLandscape.setDescription("Downloading Landscape");
                rqLandscape.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadLandscape.enqueue(rqLandscape);
                bottomSheetDialog.dismiss();
                break;
            case R.id.ln_Original_download:
                DownloadManager downloadOriginal = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uriOriginal = Uri.parse(arr.get(poss).getThumbUrl());
                DownloadManager.Request rqOriginal = new DownloadManager.Request(uriOriginal);
                rqOriginal.setTitle("Download complete");
                rqOriginal.setDescription("Downloading Original");
                rqOriginal.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadOriginal.enqueue(rqOriginal);
                bottomSheetDialog.dismiss();
                break;
            case R.id.ln_Portrait_set:
                Intent intent = new Intent(this, ActivityWallpaperCraftInstaller.class);
                intent.putExtra("setWallpaper", pathImage);
                startActivity(intent);
                arr.get(poss).setHistory(true);
                AppDatabase.getInstance(getApplicationContext()).getImagesDao().insert(arr.get(poss));
                bottomSheetDialog.dismiss();
                break;
            case R.id.ln_Landscape_set:
                Intent lancape = new Intent(this, ActivityWallpaperCraftInstaller.class);
                lancape.putExtra("setWallpaper", pathImage);
                startActivity(lancape);
                arr.get(poss).setHistory(true);
                AppDatabase.getInstance(ImageActivity.this).getImagesDao().insert(arr.get(poss));
                bottomSheetDialog.dismiss();
                break;
            case R.id.ln_Original_set:
                setWallpaperImage();
                arr.get(poss).setHistory(true);
                AppDatabase.getInstance(ImageActivity.this).getImagesDao().insert(arr.get(poss));
                Log.e("history:::", String.valueOf(arr.get(poss)));
                bottomSheetDialog.dismiss();
                break;
        }
    }

    public void downloadImage() {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.download);
        LinearLayout layoutPortrait = bottomSheetDialog.findViewById(R.id.ln_Portrait_download);
        LinearLayout layoutLandscape = bottomSheetDialog.findViewById(R.id.ln_Landscape_download);
        LinearLayout layoutOriginal = bottomSheetDialog.findViewById(R.id.ln_Original_download);
        layoutPortrait.setOnClickListener(this);
        layoutLandscape.setOnClickListener(this);
        layoutOriginal.setOnClickListener(this);
        bottomSheetDialog.show();

    }

    public void setBackgroundImage() {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.set);
        LinearLayout layoutPortrait = bottomSheetDialog.findViewById(R.id.ln_Portrait_set);
        LinearLayout layoutLandscape = bottomSheetDialog.findViewById(R.id.ln_Landscape_set);
        LinearLayout layoutOriginal = bottomSheetDialog.findViewById(R.id.ln_Original_set);
        layoutPortrait.setOnClickListener(this);
        layoutLandscape.setOnClickListener(this);
        layoutOriginal.setOnClickListener(this);
        bottomSheetDialog.show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        poss=position;
        arr.get(position);
        Log.e(TAG, arr.get(position).toString());

    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
        WallpaperManager wpm = WallpaperManager.getInstance(this);
        InputStream ins = null;
        try {
            ins = new URL(arr.get(poss).getThumbUrl()).openStream();
            wpm.setStream(ins);
            showNotification(this, "Wallpaper has been successfully set ", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void showNotification (Context context, String title, String body){
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






