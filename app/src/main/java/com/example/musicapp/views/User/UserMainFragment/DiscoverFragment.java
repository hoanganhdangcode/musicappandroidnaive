package com.example.musicapp.views.User.UserMainFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.DTO.SingerTrendingDTO;
import com.example.musicapp.DTO.SongTrendingDTO;
import com.example.musicapp.R;
import com.example.musicapp.commons.ApiService;
import com.example.musicapp.commons.RetrofitClient;
import com.example.musicapp.views.User.Adapter.SingerTrendingAdapter; // Đảm bảo import đúng
import com.example.musicapp.views.User.Adapter.SongTrendingAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverFragment extends Fragment {

    public DiscoverFragment() {
        // Constructor rỗng bắt buộc
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Gắn layout giao diện vào code
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Ánh xạ RecyclerView từ layout
        RecyclerView singerrcv = view.findViewById(R.id.rcvsingertrend);
        RecyclerView songrcv = view.findViewById(R.id.rvsongtrend);


        // 2. Setup LayoutManager (2 hàng ngang)
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
        singerrcv.setLayoutManager(gridLayoutManager);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        songrcv.setLayoutManager(linearLayoutManager);


        // 3. Khởi tạo Adapter
        // Truyền getContext() vào để singerTrendingAdapter có thể load ảnh
        SingerTrendingAdapter singerTrendingAdapter = new SingerTrendingAdapter(getContext());
        SongTrendingAdapter songTrendingAdapter = new SongTrendingAdapter(getContext());



        // 4. Gán Adapter cho RecyclerView
        singerrcv.setAdapter(singerTrendingAdapter);
        songrcv.setAdapter(songTrendingAdapter);

        // 5. Gọi hàm load dữ liệu từ API
        loadTrendingSingers(singerTrendingAdapter);
        loadTrendingSongs(songTrendingAdapter);
    }

    // Hàm gọi API
    public void loadTrendingSingers(SingerTrendingAdapter adapter) {
        ApiService apiService = RetrofitClient
                .getRetrofit("https://10.0.2.2:7007/", null) // Kiểm tra lại Port và HTTPS
                .create(ApiService.class);

        Call<List<SingerTrendingDTO>> call = apiService.getTrendingSingers();

        call.enqueue(new Callback<List<SingerTrendingDTO>>() {
            @Override
            public void onResponse(Call<List<SingerTrendingDTO>> call, Response<List<SingerTrendingDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<SingerTrendingDTO> listSingers = response.body();

                    adapter.setData(listSingers);


                } else {
                    if(getContext() != null){
                        Toast.makeText(getContext(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<SingerTrendingDTO>> call, Throwable t) {
                if(getContext() != null) {
                    Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            }
        });
    }
    public void loadTrendingSongs(SongTrendingAdapter adapter) {
        ApiService apiService = RetrofitClient
                .getRetrofit("https://10.0.2.2:7007/", null) // Kiểm tra lại Port và HTTPS
                .create(ApiService.class);

        Call<List<SongTrendingDTO>> call = apiService.getTrendingSongs();

        call.enqueue(new Callback<List<SongTrendingDTO>>() {
            @Override
            public void onResponse(Call<List<SongTrendingDTO>> call, Response<List<SongTrendingDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<SongTrendingDTO> listSongs = response.body();

                    adapter.setData(listSongs);


                } else {
                    if(getContext() != null){
                        Toast.makeText(getContext(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<SongTrendingDTO>> call, Throwable t) {
                if(getContext() != null) {
                    Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            }
        });
    }
}