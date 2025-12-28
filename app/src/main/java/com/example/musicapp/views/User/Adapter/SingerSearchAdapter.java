package com.example.musicapp.views.User.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.DTO.SingerDTO;
import com.example.musicapp.R;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SingerSearchAdapter extends RecyclerView.Adapter<SingerSearchAdapter.SingerViewHolder> {

    private Context context;
    private List<SingerDTO> singerList = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(SingerDTO singer);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SingerSearchAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<SingerDTO> list) {
        this.singerList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SingerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.discover_singer_item, parent, false);
        return new SingerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingerViewHolder holder, int position) {
        SingerDTO singer = singerList.get(position);
        if (singer == null) return;

        holder.tvName.setText(singer.getName());

        // Sử dụng Glide để load ảnh tràn CardView
        Glide.with(context)
                .load(singer.getImageUrl()) // URL từ Backend C# của bạn
                .placeholder(R.drawable.ic_more_vert) // Ảnh mặc định khi đang load
                .error(R.drawable.ic_music) // Ảnh khi lỗi
                .centerCrop() // Đảm bảo ảnh đầy Card
                .into(holder.imgsinger);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(singer);
        });
    }

    @Override
    public int getItemCount() {
        return singerList != null ? singerList.size() : 0;
    }

    public static class SingerViewHolder extends RecyclerView.ViewHolder {
        ImageView imgsinger;
        TextView tvName;

        public SingerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgsinger = itemView.findViewById(R.id.imgSingerAvatar);
            tvName = itemView.findViewById(R.id.tvSingerName);
        }
    }
}
