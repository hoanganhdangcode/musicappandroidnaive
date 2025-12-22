package com.example.musicapp.DTO;

import java.io.Serializable;

    public class SongTrendingDTO implements Serializable {
        private int songId;
        private String name;
        private String singerName; // Tên ca sĩ (cần thiết khi hiển thị item)

        private  String audioUrl;
        private String imageUrl;
        private int listenCount;

        // Constructor mặc định
        public SongTrendingDTO() {
        }

        // Constructor đầy đủ

        public SongTrendingDTO(int songId, String name, String singerName, String audioUrl, String imageUrl, int listenCount) {
            this.songId = songId;
            this.name = name;
            this.singerName = singerName;
            this.audioUrl = audioUrl;
            this.imageUrl = imageUrl;
            this.listenCount = listenCount;
        }

        public String getAudioUrl() {
            return audioUrl;
        }

        public void setAudioUrl(String audioUrl) {
            this.audioUrl = audioUrl;
        }

        // Getters and Setters
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

        public String getSingerName() {
            return singerName;
        }

        public void setSingerName(String singerName) {
            this.singerName = singerName;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public int getListenCount() {
            return listenCount;
        }

        public void setListenCount(int listenCount) {
            this.listenCount = listenCount;
        }
    }
