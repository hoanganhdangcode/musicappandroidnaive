package com.example.musicapp.views.User;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.DTO.GenreDTO;
import com.example.musicapp.DTO.SingerDTO;
import com.example.musicapp.DTO.SongTrendingDTO;
import com.example.musicapp.R;
import com.example.musicapp.commons.ApiService;
import com.example.musicapp.commons.LoggedUser;
import com.example.musicapp.commons.RetrofitClient;
import com.example.musicapp.views.User.Adapter.GenreSearchAdapter;
import com.example.musicapp.views.User.Adapter.SingerSearchAdapter;
import com.example.musicapp.views.User.Adapter.SongSearchAdapter;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    EditText searchView;

    ChipGroup chipGroup;
    TextView tvgenre,tvsong,tvsinger;
    ImageView btnsearch;

    RecyclerView rvgenre,rvsinger,rvsong;

    boolean showsong=true,showsinger = true,showgenre = true;

    GenreSearchAdapter genreadapter;
    SingerSearchAdapter singeradapter;
    SongSearchAdapter songadapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        chipGroup = findViewById(R.id.chip_group);
        tvgenre = findViewById(R.id.tvgenre);
        tvsinger = findViewById(R.id.tv_singer);
        tvsong = findViewById(R.id.tvsong);
        rvgenre = findViewById(R.id.rv_genre);
        rvsinger = findViewById(R.id.rv_singer);
        rvsong = findViewById(R.id.rv_song);
        searchView = findViewById(R.id.edt_search);
        btnsearch = findViewById(R.id.btn_search);
        btnsearch.setOnClickListener(v->{
            search();
        });




        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) {
              group.check(R.id.chip_song);
              search();
            }
            else{
                if(checkedIds.contains(R.id.chip_song)){
                    tvsong.setVisibility(View.VISIBLE);
                    rvsong.setVisibility(View.VISIBLE);
                    showsong=true;


                }
                else{
                    tvsong.setVisibility(View.GONE);
                    rvsong.setVisibility(View.GONE);
                    showsong=false;
                }
                if(checkedIds.contains(R.id.chip_singer)){
                    tvsinger.setVisibility(View.VISIBLE);
                    rvsinger.setVisibility(View.VISIBLE);
                    showsinger=true;


                }
                else{
                    tvsinger.setVisibility(View.GONE);
                    rvsinger.setVisibility(View.GONE);
                    showsinger=false;
                }
                if(checkedIds.contains(R.id.chip_genre)){
                    tvgenre.setVisibility(View.VISIBLE);
                    rvgenre.setVisibility(View.VISIBLE);
                    showgenre=true;


                }
                else{
                    tvgenre.setVisibility(View.GONE);
                    rvgenre.setVisibility(View.GONE);
                    showgenre=false;
                }



            }
        });

        genreadapter = new GenreSearchAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
        rvgenre.setLayoutManager(gridLayoutManager);
        rvgenre.setAdapter(genreadapter);
        loadGenres(genreadapter,searchView.getText().toString());


        singeradapter = new SingerSearchAdapter(this);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        rvsinger.setLayoutManager(gridLayoutManager2);
        rvsinger.setAdapter(singeradapter);
        loadSingers(singeradapter,searchView.getText().toString());

        songadapter = new SongSearchAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this,LinearLayoutManager.VERTICAL,false);
        rvsong.setLayoutManager(linearLayoutManager);
        rvsong.setAdapter(songadapter);
        loadSongs(songadapter,searchView.getText().toString());


        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(showsong){
                    loadSongs(songadapter,s.toString());
                }
                if(showsinger){
                    loadSingers(singeradapter,s.toString());
                }
                if(showgenre){
                    loadGenres(genreadapter,s.toString());
                }

            }
        });






    }

    private void search(){
        if(showsong){
            loadSongs(songadapter,searchView.getText().toString());
        }
        if(showsinger){
            loadSingers(singeradapter,searchView.getText().toString());
        }
        if(showgenre){
            loadGenres(genreadapter,searchView.getText().toString());
        }
    }

    private void loadSongs(SongSearchAdapter adapter, String keyword) {
        ApiService apiService = RetrofitClient
                .getRetrofit( LoggedUser.loggedUser.getAccessToken())
                .create(ApiService.class);
        Call<List<SongTrendingDTO>> call = apiService.searchSong(keyword);
        call.enqueue(new Callback<List<SongTrendingDTO>>() {
            @Override
            public void onResponse(Call<List<SongTrendingDTO>> call, Response<List<SongTrendingDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<SongTrendingDTO> listSong = response.body();

                    adapter.setData(listSong);


                } else {
                }
            }

            @Override
            public void onFailure(Call<List<SongTrendingDTO>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void loadSingers(SingerSearchAdapter adapter, String keyword) {
        ApiService apiService = RetrofitClient
                .getRetrofit( LoggedUser.loggedUser.getAccessToken())
                .create(ApiService.class);
        Call<List<SingerDTO>> call = apiService.adminGetSingers(keyword);
        call.enqueue(new Callback<List<SingerDTO>>() {
            @Override
            public void onResponse(Call<List<SingerDTO>> call, Response<List<SingerDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<SingerDTO> listGenre = response.body();

                    adapter.setData(listGenre);


                } else {
                }
            }

            @Override
            public void onFailure(Call<List<SingerDTO>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void loadGenres(GenreSearchAdapter adapter, String keyword) {
        ApiService apiService = RetrofitClient
                .getRetrofit( LoggedUser.loggedUser.getAccessToken())
                .create(ApiService.class);
        Call<List<GenreDTO>> call = apiService.adminGetGenres(keyword);
        call.enqueue(new Callback<List<GenreDTO>>() {
            @Override
            public void onResponse(Call<List<GenreDTO>> call, Response<List<GenreDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<GenreDTO> listGenre = response.body();

                    adapter.setData(listGenre);


                } else {
                }
            }

            @Override
            public void onFailure(Call<List<GenreDTO>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    }
