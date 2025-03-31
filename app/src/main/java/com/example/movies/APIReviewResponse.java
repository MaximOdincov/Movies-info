package com.example.movies;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class APIReviewResponse implements Serializable {
    @SerializedName("docs")
    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "APIReviewResponse{" +
                "reviews=" + reviews +
                '}';
    }

    public APIReviewResponse(List<Review> reviews) {
        this.reviews = reviews;
    }
}
