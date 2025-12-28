package com.example.musicapp.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class SongDTO {

    @SerializedName("songId")
    public int songId;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("audioUrl")
    public String audioUrl;

    @SerializedName("imageUrl")
    public String imageUrl;

    @SerializedName("createdAt")
    public String createdAt; // ISO-8601

    @SerializedName("singerId")
    public int singerId;

    @SerializedName("singerName")
    public String singerName;

    @SerializedName("genreId")
    public int genreId;

    @SerializedName("genreName")
    public String genreName;

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getSingerId() {
        return singerId;
    }

    public void setSingerId(int singerId) {
        this.singerId = singerId;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SongDTO)) return false;
        SongDTO songDTO = (SongDTO) o;
        return songId == songDTO.songId && singerId == songDTO.singerId && genreId == songDTO.genreId && Objects.equals(name, songDTO.name) && Objects.equals(description, songDTO.description) && Objects.equals(audioUrl, songDTO.audioUrl) && Objects.equals(imageUrl, songDTO.imageUrl) && Objects.equals(createdAt, songDTO.createdAt) && Objects.equals(singerName, songDTO.singerName) && Objects.equals(genreName, songDTO.genreName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(songId, name, description, audioUrl, imageUrl, createdAt, singerId, singerName, genreId, genreName);
    }
}

