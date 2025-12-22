package com.example.musicapp.commons;

import com.example.musicapp.DTO.CreateGenreDto;
import com.example.musicapp.DTO.CreateSingerDto;
import com.example.musicapp.DTO.CreateSongDto;
import com.example.musicapp.DTO.LoginDTO;
import com.example.musicapp.DTO.LoginResponseDTO;
import com.example.musicapp.DTO.RegisterDTO;
import com.example.musicapp.DTO.SingerTrendingDTO;
import com.example.musicapp.DTO.SongTrendingDTO;

import java.lang.annotation.Repeatable;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("auth/login")
    Call<LoginResponseDTO> login(@Body LoginDTO dto);
    @POST("auth/register")
    Call<Void> register(@Body RegisterDTO dto);

    @GET("user/singers/trending")
    Call<List<SingerTrendingDTO>> getTrendingSingers();

    @GET("user/songs/trending")
    Call<List<SongTrendingDTO>> getTrendingSongs();

    @PUT("user/songs/{id}/listen")
    Call<Void> listensong(@Path("id") int id);

    // 1. Thêm Ca sĩ
    @POST("admin/singer/add")
    Call<Void> addSinger(@Body CreateSingerDto dto);

    // 2. Thêm Thể loại
    @POST("admin/genre/add")
    Call<Void> addGenre(@Body CreateGenreDto dto);

    // 3. Thêm Bài hát
    @POST("admin/song/add")
    Call<Void> addSong(@Body CreateSongDto dto);
}




