package com.example.musicapp.DTO;

public class CreateSingerDto {
    private String name;
    private String description;
    private String imageUrl;

    // Constructor
    public CreateSingerDto(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters (nếu cần thiết, hoặc để public nếu lười)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}