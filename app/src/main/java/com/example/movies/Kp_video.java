package com.example.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Kp_video {
    @SerializedName("trailers")
    private List<Trailer> trailers;

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public Kp_video(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    @Override
    public String toString() {
        return "Kp_video{" +
                "trailers=" + trailers +
                '}';
    }
}
