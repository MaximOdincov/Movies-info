package com.example.movies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MoviePageActivity extends AppCompatActivity {

    private ImageView imageOfMovieView;
    private TextView nameView;
    private TextView yearView;
    private TextView descriptionView;
    private MoviePageActivityViewModel viewModel;
    private RecyclerView recyclerView;
    private TrailersAdapter trailersAdapter;
    private RecyclerView reviewRecyclerView;
    private ReviewAdapter reviewAdapter;
    private ImageView imageViewStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.scrollView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        Movie movie = (Movie)getIntent().getSerializableExtra("movie");
        setMovieInfo(movie);
        viewModel = new ViewModelProvider(this).get(MoviePageActivityViewModel.class);
        viewModel.loadTrailers(movie.getId());
        viewModel.loadReview(movie.getId());
        trailersAdapter = new TrailersAdapter();
        recyclerView.setAdapter(trailersAdapter);
        reviewAdapter = new ReviewAdapter();
        reviewRecyclerView.setAdapter(reviewAdapter);
        Drawable starOff = ContextCompat.getDrawable(MoviePageActivity.this, android.R.drawable.star_big_off);
        Drawable starOn = ContextCompat.getDrawable(MoviePageActivity.this, android.R.drawable.star_big_on);
        trailersAdapter.setOnPlayButtonClickListener(new TrailersAdapter.OnPlayButtonClickListener() {
            @Override
            public void ClickPlayButton(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });
        viewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                trailersAdapter.setTrailers(trailers);
            }
        });
        viewModel.getReview().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewAdapter.setReviews(reviews);
            }
        });
        viewModel.getFavouriteMovie(movie.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movieFromDd) {
                if(movieFromDd==null){
                    imageViewStar.setImageDrawable(starOff);
                    imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.insertMovie(movie);
                        }
                    });
                }
                else{
                    imageViewStar.setImageDrawable(starOn);
                    imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.removeMovie(movie.getId());
                        }
                    });
                }
            }
        });

    }

    private void setMovieInfo(Movie movie){
        Glide.with(this).load(movie.getPoster().getUrl()).into(imageOfMovieView);
        nameView.setText(movie.getName());
        yearView.setText(String.valueOf(movie.getYearOfRelease()));
        descriptionView.setText(movie.getDescription());
    }
    private void initView(){
        imageOfMovieView = findViewById(R.id.imageView);
        nameView = findViewById(R.id.nameView);
        yearView = findViewById(R.id.yearView);
        recyclerView = findViewById(R.id.trailerRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView = findViewById(R.id.reviewRecyclerView);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        imageViewStar = findViewById(R.id.imageViewStar);
        descriptionView = findViewById(R.id.descriptionView);
    }

    public static Intent newIntent(Context context, Movie movie){
        Intent intent = new Intent(context, MoviePageActivity.class);
        intent.putExtra("movie", movie);
        return intent;
    }
}