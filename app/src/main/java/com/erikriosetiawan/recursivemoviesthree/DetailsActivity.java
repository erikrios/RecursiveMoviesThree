package com.erikriosetiawan.recursivemoviesthree;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.erikriosetiawan.recursivemoviesthree.db.FavoriteMoviesDatabaseHelper;
import com.erikriosetiawan.recursivemoviesthree.db.FavoriteTvShowsDatabaseHelper;
import com.erikriosetiawan.recursivemoviesthree.models.Movie;
import com.erikriosetiawan.recursivemoviesthree.models.TvShow;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    public static final String KEY = "15ErSNmAfb";
    public static final String MOVIE_KEY = "ruavTTNyB2";
    public static final String TV_SHOW_KEY = "KHzxKxndNC";

    private ImageView imgPoster;
    private TextView tvTitle;
    private Button btnReleaseDate;
    private Button btnVoteCount;
    private Button btnVoteAverage;
    private TextView tvOverview;
    private ProgressDialog progressDialog;
    private MaterialFavoriteButton btnFavorite;

    int progressStatus;

    private FavoriteMoviesDatabaseHelper favoriteMoviesDatabaseHelper;
    private FavoriteTvShowsDatabaseHelper favoriteTvShowsDatabaseHelper;
    private Movie favoriteMovie;
    private TvShow favoriteTvShow;
    private final AppCompatActivity activity = DetailsActivity.this;

    private String title, releaseDate, overview, posterPath;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imgPoster = findViewById(R.id.img_detail_poster);
        tvTitle = findViewById(R.id.tv_detail_title);
        btnReleaseDate = findViewById(R.id.btn_detail_release_date);
        btnVoteCount = findViewById(R.id.btn_detail_vote_count);
        btnVoteAverage = findViewById(R.id.btn_detail_vote_average);
        tvOverview = findViewById(R.id.tv_detail_overview);
        progressDialog = new ProgressDialog(DetailsActivity.this);
        btnFavorite = findViewById(R.id.btn_favorite);
    }

    @Override
    protected void onStart() {
        super.onStart();

        showProgress();

        String key = getIntent().getStringExtra(KEY);

        switch (key) {

            case MOVIE_KEY:
                final Movie movie = getIntent().getParcelableExtra(MOVIE_KEY);
                title = movie.getTitle();
                overview = movie.getOverview();
                releaseDate = movie.getReleaseDate();
                posterPath = movie.getPosterPath();
                id = movie.getId();
                Picasso.get()
                        .load("https://image.tmdb.org/t/p/w185" + posterPath)
                        .into(imgPoster);
                tvTitle.setText(title);
                btnReleaseDate.setText(releaseDate);
                btnVoteCount.setText(String.valueOf(movie.getVoteCount()));
                btnVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
                tvOverview.setText(overview);
                setActionBar(tvTitle.getText().toString());

                SharedPreferences moviesPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                final SharedPreferences.Editor moviesEditor = moviesPreferences.edit();
                if (moviesPreferences.contains("checked") && moviesPreferences.getBoolean("checked", false)) {
                    btnFavorite.setFavorite(true);
                } else {
                    btnFavorite.setFavorite(false);
                }

                btnFavorite.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (btnFavorite.isFavorite()) {
                            moviesEditor.putBoolean("checked", true);
                            moviesEditor.apply();
                            saveFavoriteMovies(title, releaseDate, posterPath, overview, id);
                            Snackbar.make(buttonView, "Added to Favorite", Snackbar.LENGTH_SHORT).show();
                        } else {
                            favoriteMoviesDatabaseHelper = new FavoriteMoviesDatabaseHelper(DetailsActivity.this);
                            favoriteMoviesDatabaseHelper.deleteFavorite(id);
                            moviesEditor.putBoolean("checked", false);
                            moviesEditor.apply();
                            Snackbar.make(buttonView, "Removed from Favorite", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
                break;

            case TV_SHOW_KEY:
                final TvShow tvShow = getIntent().getParcelableExtra(TV_SHOW_KEY);
                title = tvShow.getName();
                overview = tvShow.getOverview();
                releaseDate = tvShow.getFirstAirDate();
                posterPath = tvShow.getPosterPath();
                id = tvShow.getId();
                Picasso.get()
                        .load("https://image.tmdb.org/t/p/w185" + tvShow.getPosterPath())
                        .into(imgPoster);
                tvTitle.setText(title);
                btnReleaseDate.setText(releaseDate);
                btnVoteCount.setText(String.valueOf(tvShow.getVoteCount()));
                btnVoteAverage.setText(String.valueOf(tvShow.getVoteAverage()));
                tvOverview.setText(overview);
                setActionBar(tvTitle.getText().toString());

                SharedPreferences tvShowsPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                final SharedPreferences.Editor tvShowsEditor = tvShowsPreferences.edit();
                if (tvShowsPreferences.contains("checked") && tvShowsPreferences.getBoolean("checked", false)) {
                    btnFavorite.setFavorite(true);
                } else {
                    btnFavorite.setFavorite(false);
                }

                btnFavorite.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (btnFavorite.isFavorite()) {
                            tvShowsEditor.putBoolean("Checked", true);
                            tvShowsEditor.apply();
                            saveFavoriteTvShows(title, releaseDate, posterPath, overview, id);
                            Snackbar.make(buttonView, "Added to Favorite", Snackbar.LENGTH_SHORT).show();
                        } else {
                            favoriteTvShowsDatabaseHelper = new FavoriteTvShowsDatabaseHelper(DetailsActivity.this);
                            favoriteTvShowsDatabaseHelper.deleteFavorite(id);
                            tvShowsEditor.putBoolean("checked", false);
                            tvShowsEditor.apply();
                            Snackbar.make(buttonView, "Removed from Favorite", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    private void setActionBar(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(DetailsActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage(getResources().getString(R.string.load_data));
        progressDialog.setTitle(getResources().getString(R.string.progress_title));
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        progressStatus = 0;
        final Handler progressHandler = new Handler();

        final Runnable runnableFinish = new Runnable() {
            @Override
            public void run() {
                progressDialog.setProgress(progressStatus);
                if (progressStatus >= 100) {
                    progressDialog.dismiss();
                }
            }
        };

        Runnable runnableMain = new Runnable() {
            @Override
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 10;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressHandler.post(runnableFinish);
                }
            }
        };
        Thread run = new Thread(runnableMain);
        run.start();
    }

    private void saveFavoriteMovies(String title, String releaseDate, String posterPath, String overview, int id) {
        favoriteMoviesDatabaseHelper = new FavoriteMoviesDatabaseHelper(activity);
        favoriteMovie = new Movie();

        favoriteMovie.setId(id);
        favoriteMovie.setTitle(title);
        favoriteMovie.setReleaseDate(releaseDate);
        favoriteMovie.setPosterPath(posterPath);
        favoriteMovie.setOverview(overview);

        favoriteMoviesDatabaseHelper.addFavorite(favoriteMovie);
    }

    private void saveFavoriteTvShows(String title, String releaseDate, String posterPath, String overview, int id) {
        favoriteTvShowsDatabaseHelper = new FavoriteTvShowsDatabaseHelper(activity);
        favoriteTvShow = new TvShow();

        favoriteTvShow.setId(id);
        favoriteTvShow.setName(title);
        favoriteTvShow.setFirstAirDate(releaseDate);
        favoriteTvShow.setPosterPath(posterPath);
        favoriteTvShow.setOverview(overview);

        favoriteTvShowsDatabaseHelper.addFavorite(favoriteTvShow);
    }


//    private void progressBarStatus(boolean status) {
//        if (status) {
//            progressDialog.setVisibility(View.VISIBLE);
//        } else {
//            progressDialog.setVisibility(View.INVISIBLE);
//        }
//    }
}
