package com.example.movies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    private static final String DB_NAME = "movie.db";

    private static MovieDatabase instance = null;

    public static MovieDatabase getInstance(Application application){
        if(instance == null){
            instance = Room.databaseBuilder(application,MovieDatabase.class, DB_NAME).build();
        }
        return instance;
    }

    abstract DataBaseDao movieDao();
}
