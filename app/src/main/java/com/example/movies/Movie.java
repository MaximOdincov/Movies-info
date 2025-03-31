package com.example.movies;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "favouriteMovies")
public class Movie implements Serializable {
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("year")
    private int yearOfRelease;
    @Embedded
    @SerializedName("poster")
    private Poster poster;
    @Embedded
    @SerializedName("rating")
    private Rating rating;
    @SerializedName("description")
    private String description;

    public void setReview(String review) {
        this.review = review;
    }

    private String review;

    public Movie(String name, int yearOfRelease, Poster poster, String description, String review, Rating rating, int id) {
        this.name = name;
        this.yearOfRelease = yearOfRelease;
        this.poster = poster;
        this.description = description;
        this.review = review;
        this.rating = rating;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Rating getRating() {
        return rating;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public Poster getPoster() {
        return poster;
    }

    public String getReview() {
        return review;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", poster=" + poster +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", review='" + review + '\'' +
                '}';
    }
}
