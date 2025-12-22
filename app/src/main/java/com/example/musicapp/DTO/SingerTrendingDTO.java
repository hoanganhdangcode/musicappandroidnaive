package com.example.musicapp.DTO;

import com.google.gson.annotations.SerializedName;

public class SingerTrendingDTO {
    @SerializedName("singerId")
    public int singerId;

    @SerializedName("name")
    public String name;

    @SerializedName("imageUrl")
    public String imageUrl;

    @SerializedName("totalListens")
    public int totalListens;

    @SerializedName("songCount")
    public int songCount;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getTotalListens() {
        return totalListens;
    }

    public void setTotalListens(int totalListens) {
        this.totalListens = totalListens;
    }

    public int getSongCount() {
        return songCount;
    }

    public void setSongCount(int songCount) {
        this.songCount = songCount;
    }

    public SingerTrendingDTO(int singerId, String name, String imageUrl, int totalListens, int songCount) {
        this.singerId = singerId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.totalListens = totalListens;
        this.songCount = songCount;
    }

    public SingerTrendingDTO() {
    }
}