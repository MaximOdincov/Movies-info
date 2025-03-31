package com.example.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    private List<Trailer> trailers = new ArrayList<>();

    private OnPlayButtonClickListener onPlayButtonClickListener;
    public void setOnPlayButtonClickListener(OnPlayButtonClickListener onPlayButtonClickListener) {
        this.onPlayButtonClickListener = onPlayButtonClickListener;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_card, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Trailer trailer = trailers.get(position);
        holder.trailerName.setText(trailer.getName());
        if(onPlayButtonClickListener!=null) {
            holder.playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPlayButtonClickListener.ClickPlayButton(trailer);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public interface OnPlayButtonClickListener{
        void ClickPlayButton(Trailer trailer);
    }

    static class TrailerViewHolder extends RecyclerView.ViewHolder{
        private TextView trailerName;
        private ImageView playButton;
        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            trailerName = itemView.findViewById(R.id.nameOfTrailer);
            playButton = itemView.findViewById(R.id.playTrailer);
        }
    }
}
