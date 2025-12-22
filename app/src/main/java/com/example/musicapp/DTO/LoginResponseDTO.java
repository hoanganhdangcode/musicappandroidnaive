package com.example.musicapp.DTO;

import com.google.gson.annotations.SerializedName;

public class LoginResponseDTO {
    @SerializedName("accessToken")
    public String accessToken;
    @SerializedName("user")
    public  UserInfoResponseDTO user;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserInfoResponseDTO getUser() {
        return user;
    }

    public void setUser(UserInfoResponseDTO user) {
        this.user = user;
    }

    public LoginResponseDTO(String accessToken, UserInfoResponseDTO user) {
        this.accessToken = accessToken;
        this.user = user;
    }

    public LoginResponseDTO() {
    }
}
