package com.example.musicapp.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class UserDTO {
    @SerializedName("userId")
    public int userId;

    @SerializedName("name")
    public String name;

    @SerializedName("email")
    public String email;

    @SerializedName("avatarUrl")
    public String avatarUrl;

    @SerializedName("role")
    public String role; // "User" | "Admin"

    @SerializedName("createdAt")
    public String createdAt; // ISO-8601

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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return userId == userDTO.userId && Objects.equals(name, userDTO.name) && Objects.equals(email, userDTO.email) && Objects.equals(avatarUrl, userDTO.avatarUrl) && Objects.equals(role, userDTO.role) && Objects.equals(createdAt, userDTO.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, email, avatarUrl, role, createdAt);
    }
}
