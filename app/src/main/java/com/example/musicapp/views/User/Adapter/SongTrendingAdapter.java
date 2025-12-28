package com.example.musicapp.views.User.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.DTO.SongTrendingDTO;
import com.example.musicapp.R;
import com.example.musicapp.views.User.PlayMusicActivity;

import java.util.List;

public class SongTrendingAdapter extends RecyclerView.Adapter<SongTrendingAdapter.SongViewHolder> {

    private Context context;
    private List<SongTrendingDTO> mListSongs;

    public SongTrendingAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<SongTrendingDTO> list) {
        this.mListSongs = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // SỬA 1: Phải trỏ đến R.layout.song_trending_item (File XML giao diện)
        // Không được trỏ vào R.drawable
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_song_item, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        SongTrendingDTO song = mListSongs.get(position);
        if (song == null) {
            return;
        }

        // 1. Gán số thứ tự (Rank)
        holder.tvRank.setText(String.valueOf(position + 1));

        // 2. Gán tên bài hát (Dùng getter)
        holder.tvName.setText(song.getName());

        // 3. Gán tên ca sĩ
        holder.tvSinger.setText(song.getSingerName());

        // 4. Gán lượt nghe (Ví dụ format đơn giản, em có thể làm hàm format 1000 -> 1K sau)
        holder.tvListenCount.setText(song.getListenCount() + " lượt nghe");

        // 5. Load ảnh bằng Glide
        Glide.with(context)
                .load(song.getImageUrl())
                .placeholder(R.drawable.loadingimg) // Ảnh tạm
                .error(R.drawable.ic_notfound)       // Ảnh lỗi
                .into(holder.imgSong);

        // Sự kiện click
        holder.itemView.setOnClickListener(v -> {
            // Xử lý khi click (ví dụ mở bài hát)
            Intent i = new Intent(v.getContext(), PlayMusicActivity.class);
            i.putExtra("songnama",song.getName());
            i.putExtra("singername",song.getSingerName());
            i.putExtra("songid",song.getSongId());
            i.putExtra("songurl",song.getAudioUrl());
            i.putExtra("avatarurl",song.getImageUrl());

            v.getContext().startActivity(i);

        });
    }

    @Override
    public int getItemCount() {
        if (mListSongs != null) {
            return mListSongs.size();
        }
        return 0;
    }

    // Class ViewHolder
    public class SongViewHolder extends RecyclerView.ViewHolder {

        // Khai báo đúng các view có trong song_trending_item.xml
        ImageView imgSong;
        TextView tvRank, tvName, tvSinger, tvListenCount;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);

            // Ánh xạ ID cho khớp với file XML
            imgSong = itemView.findViewById(R.id.imgSong);         // ID trong XML là imgSong
            tvRank = itemView.findViewById(R.id.tvRank);           // ID rank
            tvName = itemView.findViewById(R.id.tvSongName);       // Tên bài
            tvSinger = itemView.findViewById(R.id.tvSingerName);   // Tên ca sĩ
            tvListenCount = itemView.findViewById(R.id.tvListenCount); // Lượt nghe
        }
    }
}