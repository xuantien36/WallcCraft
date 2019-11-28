package com.t3h.wallccraft.adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.t3h.wallccraft.R;
import com.t3h.wallccraft.activity.ActivitySubscription;
import com.t3h.wallccraft.fragment.FragmentNewDoubleWallpaper;
import com.t3h.wallccraft.model.Phone;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DoubleWallAdapter extends RecyclerView.Adapter<DoubleWallAdapter.ImagetHolder> {
    private ArrayList<Phone> searches;
    private LayoutInflater inflater;
    private ItemClickListener listener;
    private Activity contex;

    public void setOnListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public DoubleWallAdapter(Activity context) {
        inflater = LayoutInflater.from(context);
        this.contex=context;


    }

    public void setData(ArrayList<Phone> searches) {
        this.searches = searches;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImagetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_double_wallpaper, parent, false);
        return new ImagetHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagetHolder holder, final int position) {
        Phone name = searches.get(position);
        holder.bindData(name);


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
        return searches == null ? 0 : searches.size();
    }

    public class ImagetHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.im_double_wallpaper)
        ImageView getImageView;
        @BindView(R.id.im_clock)
        ImageView view;

        public ImagetHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Phone item) {
            Glide.with(getImageView).load(item.getImgePhone()).into(getImageView);
            view.setOnClickListener(view -> {
                Intent intent=new Intent(contex, ActivitySubscription.class);

                contex.startActivity(intent);

            });

        }
    }
    public interface ItemClickListener {
        void onClicked(int position);

        void onLongClicked(int position);

    }

}
