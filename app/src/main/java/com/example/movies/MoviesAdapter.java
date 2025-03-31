package com.example.movies;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>{

    private List<Movie> movies = new ArrayList<>();

    private OnReachEndListener onReachEndListener;
    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    private OnMovieDetailClickListener onMovieDetailClickListener;
    public void setOnMovieDetailClickListener(OnMovieDetailClickListener onMovieDetailClickListener){
        this.onMovieDetailClickListener = onMovieDetailClickListener;
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Glide.with(holder.itemView).load(movie.getPoster().getUrl()).into(holder.imageViewPoster);
        holder.textViewRating.setText(movie.getRating().getKpRating().substring(0, 3));
        holder.textViewRating.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), setRatingBackground(movie.getRating().getKpRating())));
        Log.d("MoviesAdapter", String.valueOf(position));
        if((position >= movies.size()-5) && onReachEndListener!=null){
            onReachEndListener.onReachEnd();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMovieDetailClickListener.onMovieDetailClick(movie);
            }
        });
    }


    @Override
    public int getItemCount() {
        return movies.size();
    }

    interface OnReachEndListener{
        void onReachEnd();
    }

    interface OnMovieDetailClickListener{
        void onMovieDetailClick(Movie movie);
    }

    private int setRatingBackground(String rating){
        int backgroud;
        if(Double.valueOf(rating)>8){
            backgroud = R.drawable.green_circle;
        }
        else if(Double.valueOf(rating)>5){
            backgroud = R.drawable.orange_circle;
        }
        else{
            backgroud = R.drawable.red_circle;
        }
        return backgroud;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }
    static class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewPoster;
        private TextView textViewRating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            textViewRating = itemView.findViewById(R.id.textView);
        }
    }
}


