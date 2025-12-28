package com.example.musicapp.views.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.DTO.SongDTO;
import com.example.musicapp.R;
import com.example.musicapp.commons.ItemLongClickListener;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private List<SongDTO> list = new ArrayList<>();
    private Context context;
    private ItemLongClickListener<SongDTO> itemLongClickListener;


    public SongAdapter(Context context) {
        this.context = context;
    }
    public void SetItemListener(ItemLongClickListener<SongDTO> itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }


    public void setData(List<SongDTO> data) {
        list.clear();
        if (data != null) list.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_song, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        SongDTO s = list.get(position);

        h.tvName.setText(s.getName());
        h.tvSinger.setText(s.getSingerName());

        Glide.with(context)
                .load(s.getImageUrl())
                .placeholder(R.drawable.ic_more_vert)
                .error(R.drawable.ic_music)
                .into(h.imgSong);
        h.itemView.setOnLongClickListener(v->{
            if(itemLongClickListener !=null) itemLongClickListener.OnItemLongClick(s);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSong;
        TextView tvName, tvSinger;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSong = itemView.findViewById(R.id.imgSong);
            tvName = itemView.findViewById(R.id.tvName);
            tvSinger = itemView.findViewById(R.id.tvSinger);

        }

    }
}

