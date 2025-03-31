package com.example.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SavedMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_saved_movies);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        RecyclerView recyclerView = findViewById(R.id.recuclerViewSavedMovies);
        MoviesAdapter moviesAdapter = new MoviesAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(moviesAdapter);
        moviesAdapter.setOnMovieDetailClickListener(new MoviesAdapter.OnMovieDetailClickListener() {
            @Override
            public void onMovieDetailClick(Movie movie) {
                Intent intent = MoviePageActivity.newIntent(SavedMoviesActivity.this, movie);
                startActivity(intent);
            }

        });

        SavedMoviesActivityViewModel viewModel = new ViewModelProvider(this).get(SavedMoviesActivityViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesAdapter.setMovies(movies);
            }
        });

    }

    public static Intent newIntent(Context context){
        return new Intent(context, SavedMoviesActivity.class);
    }
}