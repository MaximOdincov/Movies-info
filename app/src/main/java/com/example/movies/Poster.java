package com.example.movies;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Poster implements Serializable {

    @Override
    public String toString() {
        return "Poster{" +
                "url='" + url + '\'' +
                '}';
    }

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }
    public Poster(String url) {
        this.url = url;
    }
}
