package com.example.movies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @GET("movie?rating.kp=2-10&sortField=votes.kp&sortType=-1&limit=50")
    @Headers("X-API-KEY:YOUR_API_KEY")
    Single<APIResponse> loadMovie(@Query("page") int page);
    @GET("movie/{id}")
    @Headers("X-API-KEY:YOUR_API_KEY")
    Single<APIMovieResponse> loadTrailers(@Path("id") int id);

    @GET("review?&limit=50")
    @Headers("X-API-KEY:YOUR_API_KEY")
    Single<APIReviewResponse> loadReviews(@Query("movieId") int movieId, @Query("page") int page);

}
