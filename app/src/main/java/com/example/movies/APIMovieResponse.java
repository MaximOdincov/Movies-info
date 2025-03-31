package com.example.movies;

import com.google.gson.annotations.SerializedName;

public class APIMovieResponse {
    @SerializedName("videos")
    private Kp_video video;

    public Kp_video getVideo() {
        return video;
    }

    @Override
    public String toString() {
        return "APIMovieResponse{" +
                "video=" + video +
                '}';
    }

    public APIMovieResponse(Kp_video video) {
        this.video = video;
    }
}
