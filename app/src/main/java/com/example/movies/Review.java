package com.example.movies;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Review implements Serializable {
    @SerializedName("author")
    String nameOfAuthor;
    @SerializedName("type")
    String type;
    @SerializedName("review")
    String text;
    @SerializedName("date")
    String date;
}
