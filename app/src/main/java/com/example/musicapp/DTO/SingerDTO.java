package com.example.musicapp.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class SingerDTO {
    @SerializedName("singerId")
    public int singerId;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("imageUrl")
    public String imageUrl;

    @SerializedName("createdAt")
    public String createdAt; // ISO-8601

    public int getSingerId() {
        return singerId;
    }

    public void setSingerId(int singerId) {
        this.singerId = singerId;
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
    public String toString() {
        return "SingerDTO{" +
                "singerId=" + singerId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SingerDTO)) return false;
        SingerDTO singerDTO = (SingerDTO) o;
        return singerId == singerDTO.singerId && Objects.equals(name, singerDTO.name) && Objects.equals(description, singerDTO.description) && Objects.equals(imageUrl, singerDTO.imageUrl) && Objects.equals(createdAt, singerDTO.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(singerId, name, description, imageUrl, createdAt);
    }
}
