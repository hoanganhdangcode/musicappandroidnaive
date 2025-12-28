package com.example.musicapp.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class GenreDTO {

    @SerializedName("genreId")
    public int genreId;

    @SerializedName("name")
    public String name;

    @SerializedName("imageUrl")
    public String imageUrl;

    @SerializedName("createdAt")
    public String createdAt; // ISO-8601

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GenreDTO)) return false;
        GenreDTO genreDTO = (GenreDTO) o;
        return genreId == genreDTO.genreId && Objects.equals(name, genreDTO.name) && Objects.equals(imageUrl, genreDTO.imageUrl) && Objects.equals(createdAt, genreDTO.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreId, name, imageUrl, createdAt);
    }
}
