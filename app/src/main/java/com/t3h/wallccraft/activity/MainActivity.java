package com.t3h.wallccraft.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.t3h.wallccraft.R;
import com.t3h.wallccraft.adapter.AlbumAdapter;
import com.t3h.wallccraft.apialbum.ApiBuilder;
import com.t3h.wallccraft.fragment.Fragment60Favorite;
import com.t3h.wallccraft.fragment.FragmentAll;
import com.t3h.wallccraft.fragment.FragmentDoubleWallPaper;
import com.t3h.wallccraft.fragment.FragmentExclusive;
import com.t3h.wallccraft.fragment.FragmentFavorite;
import com.t3h.wallccraft.fragment.FragmentHistory;
import com.t3h.wallccraft.model.Album;
import com.t3h.wallccraft.model.AlbumRespone;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        SearchView.OnQueryTextListener, AlbumAdapter.ItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerLayout;
    private ArrayList<Album> arr;
    private AlbumAdapter albumAdapter;
    @BindView(R.id.lv_text)
    RecyclerView recyclerView;
    @BindView(R.id.navi)
    NavigationView navigationView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.search)
    ImageView imageView;
    @BindView(R.id.title)
    TextView tvTitle;
    private FragmentTransaction fragmentTransaction;
    private int pos;


    private static CallBackSearch callBackSearch;

    public static void setCallBackSearch(CallBackSearch callBackSearch) {
        MainActivity.callBackSearch = callBackSearch;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.VISIBLE);
        initView();
        initData();
        replaceFragment(FragmentAll.newInstance(1), "");
        ApiBuilder.getInstance().getAlbum().enqueue(new Callback<AlbumRespone>() {
            @Override
            public void onResponse(Call<AlbumRespone> call, Response<AlbumRespone> response) {
                ArrayList<Album> data = response.body().getAlbum();
                if (data != null) {
                    for (Album album : data) {
                        arr.add(album);
                    }
                    progressBar.setVisibility(View.GONE);
                    albumAdapter.setAlbum(arr);

                } else {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AlbumRespone> call, Throwable t) {

            }
        });

    }

    private void initData() {
        arr = new ArrayList<>();
        arr.add(new Album(0, R.drawable.diamondabc, "Exclusive wallpapers"));
        arr.add(new Album(0, R.drawable.ic_pause_black_24dp, " Double wallpapers"));
        arr.add(new Album(0, R.drawable.ic_grade_black_24dp, " Favorites"));
        arr.add(new Album(0, R.drawable.ic_history_black_24dp, " History"));
        arr.add(new Album(0, R.drawable.crown, " Subscription"));
        arr.add(new Album(0, R.drawable.ic_settings_black_24dp, " Settings"));
        arr.add(new Album(0, R.drawable.instagramabc, " Our Instagram"));

    }

    private void initView() {
        tvTitle.setText("All");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        albumAdapter = new AlbumAdapter(this);
        recyclerView.setAdapter(albumAdapter);
        albumAdapter.setOnListener(this);
        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);


        });

    }
    public void replaceFragment(Fragment fragment, String tag) {
        try {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_main, fragment, tag);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onClicked(int position) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (position >= 0 && position <= 6) {
            switch (position) {
                case 0:
                    replaceFragment(new FragmentExclusive(), "");
                    tvTitle.setText(arr.get(position).getTitle());
                    break;

                case 1:
                    replaceFragment(new FragmentDoubleWallPaper(), "");
                    tvTitle.setText(arr.get(position).getTitle());
                    break;

                case 2:
                    replaceFragment(new FragmentFavorite(), "");
                    tvTitle.setText(arr.get(position).getTitle());
                    break;

                case 3:
                    replaceFragment(new FragmentHistory(), "");
                    tvTitle.setText(arr.get(position).getTitle());
                    break;
                case 4:
                    Intent intent = new Intent(this, ActivitySubscription.class);
                    intent.putExtra("subscription", arr.get(position).getTitle());
                    startActivity(intent);
                    break;

                case 5:
                    Intent setting = new Intent(this, ActivitySetting.class);
                    setting.putExtra("setting", arr.get(position).getTitle());
                    startActivity(setting);
                    break;

                case 6:
                    Intent stagram = new Intent(Intent.ACTION_VIEW).
                            setData(Uri.parse("http://www.instagram.com"));
                    startActivity(stagram);
                    break;
            }

        } else if (arr.get(position).getId() == 1) {
            replaceFragment(FragmentAll.newInstance(arr.get(position).getId()), "");
            tvTitle.setText(arr.get(position).getTitle());
        } else {

            replaceFragment(Fragment60Favorite.newInstance(arr.get(position).getId()), "");
            tvTitle.setText(arr.get(position).getTitle());
        }

    }

    @Override
    public void onLongClicked(int position) {

    }

    public interface CallBackSearch {
        void onQuerySearch(String text);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("bbbbbb", "bbbbb");
    }
}
