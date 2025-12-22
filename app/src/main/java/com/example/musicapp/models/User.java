package com.example.musicapp.models;

public class User {
    public int userId;
    public String name = null;
    public String email = null;
    public String avatarUrl;
    public RoleEnumType role;

    public String accessToken;

    public enum RoleEnumType{

        User,
        Admin,
    }


    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public RoleEnumType getRole() {
        return role;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setRole(RoleEnumType role) {
        this.role = role;
    }
    public void setRole(int roleint) {
       role = roleint==0?RoleEnumType.User:RoleEnumType.Admin;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public User(int userId, String name, String email, String avatarUrl, RoleEnumType role, String accessToken) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.role = role;
        this.accessToken = accessToken;
    }

    public User() {
    }
    public boolean isLogging(){
        return !(accessToken.isEmpty() ||accessToken.isBlank());
    }

}
