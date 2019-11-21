package com.t3h.wallccraft.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.t3h.wallccraft.EvenPost;
import com.t3h.wallccraft.R;
import com.t3h.wallccraft.adapter.AlbumAdapter;
import com.t3h.wallccraft.apialbum.ApiBuilder;
import com.t3h.wallccraft.fragment.Fragment60Favorite;
import com.t3h.wallccraft.fragment.Fragment60FavoriteNew;
import com.t3h.wallccraft.fragment.FragmentAll;
import com.t3h.wallccraft.fragment.FragmentDoubleWallPaper;
import com.t3h.wallccraft.fragment.FragmentExclusive;
import com.t3h.wallccraft.fragment.FragmentFavorite;
import com.t3h.wallccraft.fragment.FragmentHistory;
import com.t3h.wallccraft.fragment.FragmentNewAll;
import com.t3h.wallccraft.fragment.FragmentSetting;
import com.t3h.wallccraft.fragment.FragmentSubscription;
import com.t3h.wallccraft.model.Album;
import com.t3h.wallccraft.model.AlbumRespone;
import com.t3h.wallccraft.model.ListImage;
import com.t3h.wallccraft.model.ListImageRespone;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private SearchView searchView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;



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
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,
                FragmentAll.newInstance()).addToBackStack(null).commit();
        EventBus.getDefault().postSticky(new EvenPost(1));
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
        toolbar.setTitle("All");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        albumAdapter = new AlbumAdapter(this);
        recyclerView.setAdapter(albumAdapter);
        albumAdapter.setOnListener(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.search);
        searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
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
//        EventBus.getDefault().postSticky(new EvenPost());
//        callBackSearch.onQuerySearch(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onClicked(int position) {
        toolbar.setTitle(arr.get(position).getTitle());
        drawerLayout.closeDrawer(GravityCompat.START);
        if (position >= 0 && position <= 6) {
            switch (position) {
                case 0:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,
                            new FragmentExclusive()).addToBackStack(null).commit();
                    break;

                case 1:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,
                            new FragmentDoubleWallPaper()).addToBackStack(null).commit();
                    break;

                case 2:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,
                            new FragmentFavorite()).addToBackStack(null).commit();

                    break;

                case 3:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,
                            new FragmentHistory()).addToBackStack(null).commit();

                    break;
                case 4:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,
                            new FragmentSubscription()).addToBackStack(null).commit();

                    break;

                case 5:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,
                            new FragmentSetting()).addToBackStack(null).commit();

                    break;
                case 6:
                    Intent intent = new Intent(Intent.ACTION_VIEW).
                            setData(Uri.parse("http://www.instagram.com"));
                    startActivity(intent);
                    break;

            }
        } else {
//            getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,
//                    FragmentAll.newInstance()).addToBackStack(null).commit();
//            EventBus.getDefault().postSticky(new EvenPost(arr.get(position).getId()));

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,
                    Fragment60Favorite.newInstance()).commit();
            EventBus.getDefault().postSticky(new EvenPost(arr.get(position).getId()));
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
        finish();
        super.onBackPressed();
    }
}
