package com.erikriosetiawan.recursivemoviesthree;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.erikriosetiawan.recursivemoviesthree.models.Movie;
import com.erikriosetiawan.recursivemoviesthree.models.TvShow;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    public static final String KEY = "15ErSNmAfb";
    public static final String MOVIE_KEY = "ruavTTNyB2";
    public static final String TV_SHOW_KEY = "KHzxKxndNC";

    private ImageView imgPoster;
    private TextView tvTitle;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private ProgressDialog progressDialog;

    int progressStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imgPoster = findViewById(R.id.img_detail_poster);
        tvTitle = findViewById(R.id.tv_detail_title);
        tvReleaseDate = findViewById(R.id.tv_detail_release_date);
        tvOverview = findViewById(R.id.tv_detail_overview);
        progressDialog = new ProgressDialog(DetailsActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        showProgress();

        String key = getIntent().getStringExtra(KEY);

        switch (key) {

            case MOVIE_KEY:
                Movie movie = getIntent().getParcelableExtra(MOVIE_KEY);
                Picasso.get()
                        .load("https://image.tmdb.org/t/p/w185" + movie.getPosterPath())
                        .into(imgPoster);
                tvTitle.setText(movie.getTitle());
                tvReleaseDate.setText(movie.getReleaseDate());
                tvOverview.setText(movie.getOverview());
                setActionBar(tvTitle.getText().toString());
                break;

            case TV_SHOW_KEY:
                TvShow tvShow = getIntent().getParcelableExtra(TV_SHOW_KEY);
                Picasso.get()
                        .load("https://image.tmdb.org/t/p/w185" + tvShow.getPosterPath())
                        .into(imgPoster);
                tvTitle.setText(tvShow.getName());
                tvReleaseDate.setText(tvShow.getFirstAirDate());
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


//    private void progressBarStatus(boolean status) {
//        if (status) {
//            progressDialog.setVisibility(View.VISIBLE);
//        } else {
//            progressDialog.setVisibility(View.INVISIBLE);
//        }
//    }
}
