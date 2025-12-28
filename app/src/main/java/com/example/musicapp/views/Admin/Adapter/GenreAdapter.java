package com.example.musicapp.views.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.DTO.GenreDTO;
import com.example.musicapp.DTO.SingerDTO;
import com.example.musicapp.R;
import com.example.musicapp.commons.ItemClickListener;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {

    private final List<GenreDTO> genres = new ArrayList<>();
    private final Context context;
    private ItemClickListener<GenreDTO> listener;

    public GenreAdapter(Context context) {
        this.context = context;
    }
    public void SetItemListener(ItemClickListener<GenreDTO> itemClickListener) {
        this.listener = itemClickListener;
    }

    public void setData(List<GenreDTO> data) {
        genres.clear();
        if (data != null) genres.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_genre, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        GenreDTO g = genres.get(position);

        holder.tvName.setText(g.getName());

        Glide.with(context)
                .load(g.getImageUrl())
                .placeholder(R.drawable.ic_more_vert)
                .error(R.drawable.ic_category)
                .into(holder.imgGenre);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.OnItemClick(g);
        });
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    static class GenreViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGenre;
        TextView tvName;

        GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGenre = itemView.findViewById(R.id.imgGenre);
            tvName = itemView.findViewById(R.id.tvGenreName);
        }
    }
}
