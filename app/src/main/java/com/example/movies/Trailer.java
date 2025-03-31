package com.example.movies;

import com.google.gson.annotations.SerializedName;

public class Trailer {
    public Trailer(String url, String name) {
        this.url = url;
        this.name = name;
    }
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Trailer{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
