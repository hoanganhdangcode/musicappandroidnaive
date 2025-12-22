package com.example.musicapp.DTO;

import com.example.musicapp.models.User;
import com.google.gson.annotations.SerializedName;

public class UserInfoResponseDTO {
    @SerializedName("userId")
    public int userId;
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("avatarUrl")
    public String avatarurl;
    @SerializedName("role")
    public int role;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public UserInfoResponseDTO(int userId, String name, String email, String avatarurl, int role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.avatarurl = avatarurl;
        this.role = role;
    }

    public UserInfoResponseDTO() {
    }
}


