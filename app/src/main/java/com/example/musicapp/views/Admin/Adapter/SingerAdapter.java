package com.example.musicapp.views.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.DTO.SingerDTO;
import com.example.musicapp.DTO.SongDTO;
import com.example.musicapp.R;
import com.example.musicapp.commons.ItemClickListener;
import com.example.musicapp.commons.ItemLongClickListener;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SingerAdapter extends RecyclerView.Adapter<SingerAdapter.SingerViewHolder> {

    private final List<SingerDTO> singers = new ArrayList<>();
    private final Context context;

    private ItemClickListener<SingerDTO> listener;

    public SingerAdapter(Context context) {
        this.context = context;
    }
    public void SetItemListener(ItemClickListener<SingerDTO> itemClickListener) {
        this.listener = itemClickListener;
    }



    public void setData(List<SingerDTO> data) {
        singers.clear();
        if (data != null) singers.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SingerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_singer, parent, false);
        return new SingerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingerViewHolder holder, int position) {
        SingerDTO s = singers.get(position);

        holder.tvName.setText(s.getName());
        holder.tvDescription.setText(s.getDescription());

        Glide.with(context)
                .load(s.getImageUrl())
                .placeholder(R.drawable.ic_more_vert)
                .error(R.drawable.ic_user)
                .into(holder.imgSinger);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.OnItemClick(s);
        });
    }

    @Override
    public int getItemCount() {
        return singers.size();
    }

    static class SingerViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSinger;
        TextView tvName, tvDescription;

        SingerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSinger = itemView.findViewById(R.id.imgSinger);
            tvName = itemView.findViewById(R.id.tvSingerName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }
}
