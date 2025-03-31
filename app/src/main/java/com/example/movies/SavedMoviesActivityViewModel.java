package com.example.movies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SavedMoviesActivityViewModel extends AndroidViewModel {
    public SavedMoviesActivityViewModel(@NonNull Application application) {
        super(application);
        db = MovieDatabase.getInstance(application).movieDao();
    }
    private final DataBaseDao db;

    public LiveData<List<Movie>>getMovies(){
        return db.getFavouriteMovies();
    }
}
