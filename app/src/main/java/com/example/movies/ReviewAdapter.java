package com.example.movies;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private List<Review> reviews = new ArrayList<>();

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_card, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.textOfReview.setText(review.text);
        holder.authorName.setText(review.nameOfAuthor);
        holder.date.setText(changeFormatOfDate(review.date));
        int avatar = chooseAvatar(review.type);
        holder.avatar.setImageResource(avatar);
        Log.d("ReviewAdapter", String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    private String changeFormatOfDate(String date){
        String year = date.substring(0, 5);
        String month = date.substring(5, 7);
        String day = date.substring(7, 9);
        return day+"."+month+"."+year;
    }

    private int chooseAvatar(String type){
        if(type.equals("Позитивный")){
            return R.drawable.kind_avatar;
        }
        else if(type.equals("Негативный")){
            return R.drawable.angry_avatar;
        }
        else{
            return R.drawable.confused_avatar;
        }
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder{
        TextView authorName;
        TextView textOfReview;
        TextView date;
        ImageView avatar;
        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            authorName = itemView.findViewById(R.id.nameAuthor);
            textOfReview = itemView.findViewById(R.id.textOfReview);
            date = itemView.findViewById(R.id.dateOfReview);
            avatar = itemView.findViewById(R.id.authorAvatar);
        }
    }
}
