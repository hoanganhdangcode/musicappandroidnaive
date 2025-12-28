package com.example.musicapp.views.User.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.DTO.GenreDTO;
import com.example.musicapp.R;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class GenreSearchAdapter extends RecyclerView.Adapter<GenreSearchAdapter.GenreViewHolder> {

    private Context context;
    private List<GenreDTO> genreList = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(GenreDTO genre);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public GenreSearchAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<GenreDTO> list) {
        this.genreList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_genre_search, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        GenreDTO genre = genreList.get(position);
        if (genre == null) return;

        holder.tvName.setText(genre.getName());

        // Sử dụng Glide để load ảnh tràn CardView
        Glide.with(context)
                .load(genre.getImageUrl()) // URL từ Backend C# của bạn
                .placeholder(R.drawable.ic_more_vert) // Ảnh mặc định khi đang load
                .error(R.drawable.ic_music) // Ảnh khi lỗi
                .centerCrop() // Đảm bảo ảnh đầy Card
                .into(holder.imgGenre);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(genre);
        });
    }

    @Override
    public int getItemCount() {
        return genreList != null ? genreList.size() : 0;
    }

    public static class GenreViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGenre;
        TextView tvName;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGenre = itemView.findViewById(R.id.img_genre);
            tvName = itemView.findViewById(R.id.tv_genre_name);
        }
    }
}
