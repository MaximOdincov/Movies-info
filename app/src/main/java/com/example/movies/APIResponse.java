package com.example.movies;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class APIResponse {
    public List<Movie> getMovies() {
        return movies;
    }
    @SerializedName("docs")
    private List<Movie> movies;

    public APIResponse(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "APIResponse{" +
                "movies=" + movies +
                '}';
    }
}
