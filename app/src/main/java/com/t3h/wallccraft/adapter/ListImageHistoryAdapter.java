package com.t3h.wallccraft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.t3h.wallccraft.R;
import com.t3h.wallccraft.dao.AppDatabase;
import com.t3h.wallccraft.model.ListImage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListImageHistoryAdapter extends RecyclerView.Adapter<ListImageHistoryAdapter.ListImageHolder> {
    private ArrayList<ListImage> data;
    private LayoutInflater inflater;
    private ItemClickListener listener;
    private Context context;

    public void setOnListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public ListImageHistoryAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;

    }

    public void setData(ArrayList<ListImage> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_history, parent, false);
        return new ListImageHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListImageHolder holder, final int position) {
        ListImage name = data.get(position);
        holder.bindData(name);
        holder.imageView.setOnClickListener(view -> {
                AppDatabase.getInstance(context).getImagesDao().deleteHistory(data.remove(position));
                notifyDataSetChanged();
        });



        if (listener != null) {
            holder.itemView.setOnClickListener(view -> listener.onClicked(position));

            holder.itemView.setOnLongClickListener(view -> {
                listener.onLongClicked(position);
                return true;
            });
        }


    }
    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ListImageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.im_image)
        ImageView view;
        @BindView(R.id.tv_Name_Image)
        TextView textView;
        @BindView(R.id.im_close_history)
        AppCompatImageView imageView;
        @BindView(R.id.tv_history)
        TextView tvHistory;


        public ListImageHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ListImage item) {
            Glide.with(view).load(item.getThumbUrl()).into(view);
            textView.setText(item.getTitle());
        }
    }

    public interface ItemClickListener {
        void onClicked(int position);

        void onLongClicked(int position);

    }

}
