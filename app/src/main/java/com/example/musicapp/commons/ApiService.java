package com.example.musicapp.commons;

import com.example.musicapp.DTO.CloudianySignatureDTO;
import com.example.musicapp.DTO.GenreDTO;

import com.example.musicapp.DTO.UserDTO;
import com.example.musicapp.DTO.CreateGenreDto;
import com.example.musicapp.DTO.CreateSingerDto;
import com.example.musicapp.DTO.CreateSongDto;
import com.example.musicapp.DTO.LoginDTO;
import com.example.musicapp.DTO.LoginResponseDTO;
import com.example.musicapp.DTO.RegisterDTO;
import com.example.musicapp.DTO.SingerDTO;
import com.example.musicapp.DTO.SingerTrendingDTO;
import com.example.musicapp.DTO.SongDTO;
import com.example.musicapp.DTO.SongTrendingDTO;
import com.example.musicapp.DTO.UserInfoResponseDTO;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("auth/login")
    Call<LoginResponseDTO> login(@Body LoginDTO dto);
    @GET("auth/info")
    Call<UserInfoResponseDTO> getInfo();


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

    @GET("admin/songs")
    Call<List<SongDTO>> adminGetSongs(
            @Query("keyword") String keyword
    );

    @GET("admin/singers")
    Call<List<SingerDTO>> adminGetSingers(
            @Query("keyword") String keyword
    );

    @GET("admin/genres")
    Call<List<GenreDTO>> adminGetGenres(
            @Query("keyword") String keyword
    );

    @GET("admin/users")
    Call<List<UserDTO>> adminGetUsers(
            @Query("keyword") String keyword
    );
    @DELETE("/admin/song/delete/{id}")
    Call<Void> deleteSong(@Path("id") int songId);
    @PUT("/admin/song/update/{id}")
    Call<Void> updateSong(
            @Path("id") int id,
            @Body SongDTO songDto
    );
    @PUT("/admin/genre/update/{id}")
    Call<Void> updateGenre(
            @Path("id") int id,
            @Body GenreDTO genreDTO
    );
    @DELETE("/admin/genre/delete/{id}")
    Call<Void> deleteGenre(@Path("id") int genreId);


    @GET("admin/upload/signature")
    Call<CloudianySignatureDTO> getUploadSignature();

    @GET ("/user/songs")
        Call<List<SongTrendingDTO>> searchSong(@Query("keyword") String keyword);






}




