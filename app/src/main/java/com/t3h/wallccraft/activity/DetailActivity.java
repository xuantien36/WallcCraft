package com.t3h.wallccraft.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.t3h.wallccraft.R;
import com.t3h.wallccraft.dao.AppDatabase;
import com.t3h.wallccraft.model.ListImage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ListImage image;
    @BindView(R.id.im_Detail)
    ImageView imageView;
    @BindView(R.id.download)
    Button btnDownload;
    @BindView(R.id.set)
    Button btnSet;
    private boolean isFavorite = false;
    private Handler handler;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private BottomSheetDialog bottomSheetDialog;
    private int REQUEST_CODE_IMAGE = 1;
    String urlImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.VISIBLE);
        handler = new Handler();
        handler.postDelayed(() -> progressBar.setVisibility(View.GONE), 500);
        initView();

    }

    private void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        image = (ListImage) intent.getSerializableExtra("data");
        Glide.with(imageView).load(image.getThumbUrl()).into(imageView);
        btnDownload.setOnClickListener(this);
        btnSet.setOnClickListener(this);


    }

    public void selectFavorite(MenuItem item) {
        isFavorite = !image.isFavorite();
        item.setIcon(isFavorite ? R.drawable.ic_grade_black_24dp : R.drawable.ic_star_border_black_24dp);
        image.setFavorite(isFavorite);
        if (isFavorite) {
            AppDatabase.getInstance(getApplicationContext()).getImagesDao().insert(image);
        } else {
            AppDatabase.getInstance(getApplicationContext()).getImagesDao().delete(image);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        MenuItem item = menu.findItem(R.id.favorite_detail);
        ListImage im = AppDatabase.getInstance(getApplicationContext()).getImagesDao().getImageById((image.getId()));
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
                intent.putExtra("filter", image.getThumbUrl());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            urlImage = data.getStringExtra("image");
            Glide.with(this).load(urlImage).into(imageView);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.download:
                downloadImage();
                break;
            case R.id.set:
                setBackgroundImage();
                break;
            case R.id.ln_Portrait_download:
                DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(image.getThumbUrl());
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle("Download complete");
                request.setDescription("Downloading");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadmanager.enqueue(request);
                bottomSheetDialog.dismiss();


                break;
            case R.id.ln_Landscape_download:
                DownloadManager downloadLandscape = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri ui = Uri.parse(image.getThumbUrl());
                DownloadManager.Request rqLandscape = new DownloadManager.Request(ui);
                rqLandscape.setTitle("Download complete");
                rqLandscape.setDescription("Downloading Landscape");
                rqLandscape.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadLandscape.enqueue(rqLandscape);
                bottomSheetDialog.dismiss();
                break;
            case R.id.ln_Original_download:
                DownloadManager downloadOriginal = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uriOriginal = Uri.parse(image.getThumbUrl());
                DownloadManager.Request rqOriginal = new DownloadManager.Request(uriOriginal);
                rqOriginal.setTitle("Download complete");
                rqOriginal.setDescription("Downloading Original");
                rqOriginal.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadOriginal.enqueue(rqOriginal);
                bottomSheetDialog.dismiss();
                break;
            case R.id.ln_Portrait_set:
                setWallpaperImage();
                image.setHistory(true);
                AppDatabase.getInstance(this).getImagesDao().insert(image);
                bottomSheetDialog.dismiss();
                break;
            case R.id.ln_Landscape_set:
                setWallpaperImage();
                image.setHistory(true);
                AppDatabase.getInstance(this).getImagesDao().insert(image);
                Log.e("insert", image.toString());
                bottomSheetDialog.dismiss();
                break;
            case R.id.ln_Original_set:
                setWallpaperImage();
                image.setHistory(true);
                AppDatabase.getInstance(this).getImagesDao().insert(image);
                bottomSheetDialog.dismiss();
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

        WallpaperManager wpm = WallpaperManager.getInstance(this);
        InputStream ins = null;
        try {
            ins = new URL(image.getThumbUrl()).openStream();
            wpm.setStream(ins);
            showNotification(this, "Wallpaper has been successfully set ", "");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("eee::", e.getMessage());
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

}




