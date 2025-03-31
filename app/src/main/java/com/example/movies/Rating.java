package com.example.movies;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rating implements Serializable {
    private String rezhalikRating;
    @SerializedName("kp")
    private String kpRating;
    public String getKpRating() {
        return kpRating;
    }

    public void setRezhalikRating(String rezhalikRating) {
        this.rezhalikRating = rezhalikRating;
    }

    public String getRezhalikRating() {
        return rezhalikRating;
    }

    public Rating(String kpRating) {
        this.kpRating = kpRating;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "rezhalikRating='" + rezhalikRating + '\'' +
                ", kpRating='" + kpRating + '\'' +
                '}';
    }
}
