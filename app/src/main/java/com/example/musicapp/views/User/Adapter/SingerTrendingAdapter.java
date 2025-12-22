package com.example.musicapp.views.User.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.DTO.SingerTrendingDTO;
import com.example.musicapp.R;

import java.util.List;

public class SingerTrendingAdapter extends RecyclerView.Adapter<SingerTrendingAdapter.SingerViewHolder> {

    private Context context;
    private List<SingerTrendingDTO> mListSingers;

    // Constructor cần Context để load ảnh bằng Glide
    public SingerTrendingAdapter(Context context) {
        this.context = context;
    }

    // Hàm này quan trọng: Dùng để cập nhật dữ liệu mới từ API
    public void setData(List<SingerTrendingDTO> list) {
        this.mListSingers = list;
        notifyDataSetChanged(); // Báo cho RecyclerView vẽ lại
    }

    @NonNull
    @Override
    public SingerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Ánh xạ file layout item_singer_trending.xml vào Java
        View view = LayoutInflater.from(parent.getContext()).inflate(R.drawable.discover_singer_item, parent, false);
        return new SingerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingerViewHolder holder, int position) {
        SingerTrendingDTO singer = mListSingers.get(position);
        if (singer == null) {
            return;
        }

        // 1. Gán tên
        holder.tvName.setText(singer.name);

        // 2. Gán thông tin phụ (Số bài hát - Tổng lượt nghe)
//        String info = singer.songCount + " bài hát • " + singer.totalListens + " lượt nghe";
//        holder.tvInfo.setText(info);

        // 3. Load ảnh từ URL bằng Glide
        // Lưu ý: Nếu ảnh bị lỗi hoặc null thì hiện ảnh mặc định (placeholder)
        Glide.with(context)
                .load(singer.imageUrl)
                .placeholder(R.mipmap.ic_launcher) // Ảnh chờ khi đang tải
                .error(R.mipmap.ic_launcher)       // Ảnh hiện khi lỗi
                .into(holder.imgAvatar);

        // (Optional) Xử lý sự kiện click vào item
        holder.itemView.setOnClickListener(v -> {
            // Xử lý khi click vào ca sĩ (ví dụ chuyển sang màn hình danh sách bài hát)
            // Toast.makeText(context, "Đã chọn: " + singer.name, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        if (mListSingers != null) {
            return mListSingers.size();
        }
        return 0;
    }

    // Class ViewHolder: Khai báo các view trong item_singer_trending.xml
    public class SingerViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAvatar;
        TextView tvName, tvInfo;

        public SingerViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.imgSingerAvatar);
            tvName = itemView.findViewById(R.id.tvSingerName);
        }
    }
}