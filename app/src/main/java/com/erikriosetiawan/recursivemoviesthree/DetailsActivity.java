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

import com.erikriosetiawan.recursivemoviesthree.db.FavoriteDatabaseContract;
import com.erikriosetiawan.recursivemoviesthree.db.FavoriteMoviesDatabaseHelper;
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
    private Movie favoriteMovie;
    private final AppCompatActivity activity = DetailsActivity.this;

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
                Movie movie = getIntent().getParcelableExtra(MOVIE_KEY);
                final String posterPath = movie.getPosterPath();
                final int movieId = movie.getId();
                Picasso.get()
                        .load("https://image.tmdb.org/t/p/w185" + posterPath)
                        .into(imgPoster);
                tvTitle.setText(movie.getTitle());
                btnReleaseDate.setText(movie.getReleaseDate());
                btnVoteCount.setText(String.valueOf(movie.getVoteCount()));
                btnVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
                tvOverview.setText(movie.getOverview());
                setActionBar(tvTitle.getText().toString());

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                final SharedPreferences.Editor editor = preferences.edit();
                if (preferences.contains("checked") && preferences.getBoolean("checked", false)) {
                    btnFavorite.setFavorite(true);
                } else {
                    btnFavorite.setFavorite(false);
                }

                btnFavorite.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (btnFavorite.isFavorite()) {
                            editor.putBoolean("checked", true);
                            editor.apply();
                            String title = tvTitle.getText().toString();
                            String releaseDate = btnReleaseDate.getText().toString();
                            String overview = tvOverview.getText().toString();
                            saveFavoriteMovies(title, releaseDate, posterPath, overview);
                            Snackbar.make(buttonView, "Added to Favorite", Snackbar.LENGTH_SHORT).show();
                        } else {
                            favoriteMoviesDatabaseHelper = new FavoriteMoviesDatabaseHelper(DetailsActivity.this);
                            favoriteMoviesDatabaseHelper.deleteFavorite(movieId);
                            editor.putBoolean("checked", false);
                            editor.apply();
                            Snackbar.make(buttonView, "Removed from Favorite", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
                break;

            case TV_SHOW_KEY:
                TvShow tvShow = getIntent().getParcelableExtra(TV_SHOW_KEY);
                Picasso.get()
                        .load("https://image.tmdb.org/t/p/w185" + tvShow.getPosterPath())
                        .into(imgPoster);
                tvTitle.setText(tvShow.getName());
                btnReleaseDate.setText(tvShow.getFirstAirDate());
                btnVoteCount.setText(String.valueOf(tvShow.getVoteCount()));
                btnVoteAverage.setText(String.valueOf(tvShow.getVoteAverage()));
                tvOverview.setText(tvShow.getOverview());
                setActionBar(tvTitle.getText().toString());
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

    private void saveFavoriteMovies(String title, String releaseDate, String posterPath, String overview) {
        favoriteMoviesDatabaseHelper = new FavoriteMoviesDatabaseHelper(activity);
        favoriteMovie = new Movie();

        favoriteMovie.setTitle(title);
        favoriteMovie.setReleaseDate(releaseDate);
        favoriteMovie.setPosterPath(posterPath);
        favoriteMovie.setOverview(overview);

        favoriteMoviesDatabaseHelper.addFavorite(favoriteMovie);
    }


//    private void progressBarStatus(boolean status) {
//        if (status) {
//            progressDialog.setVisibility(View.VISIBLE);
//        } else {
//            progressDialog.setVisibility(View.INVISIBLE);
//        }
//    }
}
