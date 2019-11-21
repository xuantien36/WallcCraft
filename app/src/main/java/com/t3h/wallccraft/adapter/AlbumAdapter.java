package com.t3h.wallccraft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.t3h.wallccraft.R;
import com.t3h.wallccraft.model.Album;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> {
    private ArrayList<Album> searches;
    private LayoutInflater inflater;
    private ItemClickListener listener;
    private Context context;
    private int pos = 7;

    public void setOnListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public AlbumAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;

    }

    public void setAlbum(ArrayList<Album> searches) {
        this.searches = searches;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_album, parent, false);
        return new AlbumHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder holder, final int position) {
        Album name = searches.get(position);
        holder.bindData(name);
        if (searches.get(position).getTitle() == "All") {
            holder.nameSearch.setTextColor(context.getResources().getColor(R.color.colorText));
        }
        if (listener != null) {
            holder.itemView.setOnClickListener(view -> {
                listener.onClicked(position);
                pos = position;
                notifyDataSetChanged();

            });
            holder.itemView.setOnLongClickListener(view -> {
                listener.onLongClicked(position);
                return true;
            });

            if (pos == position) {
                holder.nameSearch.setTextColor(context.getResources().getColor(R.color.colorText));
                holder.ivIcon.setColorFilter(holder.ivIcon.getContext().getResources().getColor(R.color.colorText));

            } else {
                holder.nameSearch.setTextColor(context.getResources().getColor(R.color.colorTextheader));
            }

        }

    }

    @Override
    public int getItemCount() {
        return searches == null ? 0 : searches.size();
    }

    public class AlbumHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_Name)
        TextView nameSearch;
        @BindView(R.id.ivIcon)
        ImageView ivIcon;

        public AlbumHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Album item) {
            nameSearch.setText(item.getTitle());
            if (item.getIcon() != 0) {
                ivIcon.setVisibility(View.VISIBLE);
                ivIcon.setImageResource(item.getIcon());
            } else {
                ivIcon.setVisibility(View.GONE);
                ivIcon.setImageResource(0);
            }
        }

    }
    public interface ItemClickListener {
        void onClicked(int position);

        void onLongClicked(int position);
    }


}
