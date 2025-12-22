package com.example.musicapp.DTO;

public class CreateSongDto {
    private String name;
    private String description;
    private String audioUrl;
    private String imageUrl;
    private int singerId; // Quan trọng: ID của ca sĩ
    private int genreId;  // Quan trọng: ID của thể loại

    public CreateSongDto(String name, String description, String audioUrl, String imageUrl, int singerId, int genreId) {
        this.name = name;
        this.description = description;
        this.audioUrl = audioUrl;
        this.imageUrl = imageUrl;
        this.singerId = singerId;
        this.genreId = genreId;
    }

    // Getters Setters...
}