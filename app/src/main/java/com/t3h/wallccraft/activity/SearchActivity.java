package com.t3h.wallccraft.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.t3h.wallccraft.R;
import com.t3h.wallccraft.adapter.ListImageAdapter;
import com.t3h.wallccraft.apialbum.ApiBuilder;
import com.t3h.wallccraft.model.ListAlbumRespone;
import com.t3h.wallccraft.model.ListImage;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.im_back)
    ImageView imageBack;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.im_close)
    ImageView imageClose;
    private ListImageAdapter adapter;
    @BindView(R.id.lv_search)
    RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        imageBack.setOnClickListener(this);
        imageClose.setOnClickListener(this);
        edtSearch.addTextChangedListener(this);
        edtSearch.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                callApiSearch(edtSearch.getText().toString());
                return true;
            }
            return false;
        });
        initView();

    }

    private void initView() {
        adapter = new ListImageAdapter(this);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);
    }


    private void callApiSearch(String text) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading........");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        ApiBuilder.getInstance().getAlbumSearch(text).enqueue(new Callback<ListAlbumRespone>() {
            @Override
            public void onResponse(Call<ListAlbumRespone> call, Response<ListAlbumRespone> response) {
                List<ListImage> listImage = response.body().getListImage();
                if (listImage != null) {
                    progressDialog.dismiss();
                    adapter.setData((ArrayList<ListImage>) listImage);
                    hideSoftKeyboard(SearchActivity.this);
                    swipeRefreshLayout.setRefreshing(false);

                }
            }

            @Override
            public void onFailure(Call<ListAlbumRespone> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                finish();
                break;
            case R.id.im_close:
                edtSearch.getText().clear();
                break;
        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        imageClose.setVisibility(View.VISIBLE);
    }


    @Override
    public void afterTextChanged(Editable editable) {

    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);

    }
}
