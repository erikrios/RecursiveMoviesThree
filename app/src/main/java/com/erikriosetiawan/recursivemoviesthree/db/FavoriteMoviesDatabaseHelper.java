package com.erikriosetiawan.recursivemoviesthree.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.erikriosetiawan.recursivemoviesthree.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMoviesDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favoritemovies.db";
    private static final int DATABASE_VERSION = 1;
    public static final String LOG_TAG = FavoriteMoviesDatabaseHelper.class.getSimpleName();

    SQLiteOpenHelper dbHandler;
    SQLiteDatabase db;

    public FavoriteMoviesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() {
        Log.i(LOG_TAG, "Database Opened");
        db = dbHandler.getWritableDatabase();
    }

    public void close() {
        Log.i(LOG_TAG, "Database Closed");
        dbHandler.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVORITE_MOVIES_TABLE = "CREATE TABLE " + FavoriteDatabaseContract.FavoriteMoviesEntry.TABLE_NAME + " (" +
                FavoriteDatabaseContract.FavoriteMoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoriteDatabaseContract.FavoriteMoviesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavoriteDatabaseContract.FavoriteMoviesEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                FavoriteDatabaseContract.FavoriteMoviesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                FavoriteDatabaseContract.FavoriteMoviesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL" + ");";

        db.execSQL(SQL_CREATE_FAVORITE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteDatabaseContract.FavoriteMoviesEntry.TABLE_NAME);
        onCreate(db);
    }

    public void addFavorite(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FavoriteDatabaseContract.FavoriteMoviesEntry.COLUMN_TITLE, movie.getTitle());
        values.put(FavoriteDatabaseContract.FavoriteMoviesEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        values.put(FavoriteDatabaseContract.FavoriteMoviesEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(FavoriteDatabaseContract.FavoriteMoviesEntry.COLUMN_OVERVIEW, movie.getOverview());

        db.insert(FavoriteDatabaseContract.FavoriteMoviesEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void deleteFavorite(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavoriteDatabaseContract.FavoriteMoviesEntry.TABLE_NAME, FavoriteDatabaseContract.FavoriteMoviesEntry._ID + " = '" + id + "'", null);
    }

    public List<Movie> getAllFavorite() {
        String[] columns = {
                FavoriteDatabaseContract.FavoriteMoviesEntry._ID,
                FavoriteDatabaseContract.FavoriteMoviesEntry.COLUMN_TITLE,
                FavoriteDatabaseContract.FavoriteMoviesEntry.COLUMN_RELEASE_DATE,
                FavoriteDatabaseContract.FavoriteMoviesEntry.COLUMN_POSTER_PATH,
                FavoriteDatabaseContract.FavoriteMoviesEntry.COLUMN_OVERVIEW,
        };
        String sortOrder = FavoriteDatabaseContract.FavoriteMoviesEntry._ID + " ASC";
        List<Movie> favoriteList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FavoriteDatabaseContract.FavoriteMoviesEntry.TABLE_NAME, columns, null, null, null, null, sortOrder);

        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setTitle(cursor.getString(cursor.getColumnIndex(FavoriteDatabaseContract.FavoriteMoviesEntry.COLUMN_TITLE)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(FavoriteDatabaseContract.FavoriteMoviesEntry.COLUMN_RELEASE_DATE)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(FavoriteDatabaseContract.FavoriteMoviesEntry.COLUMN_POSTER_PATH)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(FavoriteDatabaseContract.FavoriteMoviesEntry.COLUMN_OVERVIEW)));

                favoriteList.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return favoriteList;
    }
}