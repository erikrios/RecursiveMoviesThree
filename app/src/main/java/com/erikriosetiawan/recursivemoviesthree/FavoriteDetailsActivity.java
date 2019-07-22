package com.erikriosetiawan.recursivemoviesthree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class FavoriteDetailsActivity extends AppCompatActivity {

    private ImageView imgPosterFavorite;
    private TextView tvTitleFavorite;
    private Button btnReleaseDateFavorite;
    private TextView tvOverviewFavorite;

    public static final String FAVORITE_KEY = "99jk4kjsaf";
    public static final String FAVORITE_MOVIE_KEY = "hgdt5y8Zpv";
    public static final String FAVORITE_MOVIE_POSTER_KEY = "uinkdI8ojLj";
    public static final String FAVORITE_MOVIE_TITLE_KEY = "pinldI8ojLj";
    public static final String FAVORITE_MOVIE_RELEASE_DATE_KEY = "minkdI0ojPj";
    public static final String FAVORITE_MOVIE_OVERVIEW_KEY = "qinbdI8AjLj";
    public static final String FAVORITE_TV_SHOW_KEY = "oejh78FDtr";
    public static final String FAVORITE_TV_SHOW_POSTER_KEY = "dfgDg543Dv";
    public static final String FAVORITE_TV_SHOW_TITLE_KEY = "Fn4gDg54kD0";
    public static final String FAVORITE_TV_SHOW_RELEASE_DATE_KEY = "LfgDg543Dz";
    public static final String FAVORITE_TV_SHOW_OVERVIEW_KEY = "12gbg54KDv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_details);

        imgPosterFavorite = findViewById(R.id.img_favorite_detail_poster);
        tvTitleFavorite = findViewById(R.id.tv_favorite_detail_title);
        btnReleaseDateFavorite = findViewById(R.id.btn_favorite_detail_release_date);
        tvOverviewFavorite = findViewById(R.id.tv_favorite_detail_overview);
    }

    @Override
    protected void onStart() {
        super.onStart();

        String favoriteKey = getIntent().getStringExtra(FAVORITE_KEY);
        String posterPath, title, releaseDate, overview;

        switch (favoriteKey) {
            case FAVORITE_MOVIE_KEY:
                posterPath = getIntent().getStringExtra(FAVORITE_MOVIE_POSTER_KEY);
                title = getIntent().getStringExtra(FAVORITE_MOVIE_TITLE_KEY);
                releaseDate = getIntent().getStringExtra(FAVORITE_MOVIE_RELEASE_DATE_KEY);
                overview = getIntent().getStringExtra(FAVORITE_MOVIE_OVERVIEW_KEY);

                Picasso.get()
                        .load("https://image.tmdb.org/t/p/w185" + posterPath)
                        .into(imgPosterFavorite);
                tvTitleFavorite.setText(title);
                btnReleaseDateFavorite.setText(releaseDate);
                tvOverviewFavorite.setText(overview);

                setActionBar(title);
                break;

            case FAVORITE_TV_SHOW_KEY:
                posterPath = getIntent().getStringExtra(FAVORITE_TV_SHOW_POSTER_KEY);
                title = getIntent().getStringExtra(FAVORITE_TV_SHOW_TITLE_KEY);
                releaseDate = getIntent().getStringExtra(FAVORITE_TV_SHOW_RELEASE_DATE_KEY);
                overview = getIntent().getStringExtra(FAVORITE_TV_SHOW_OVERVIEW_KEY);

                Picasso.get()
                        .load("https://image.tmdb.org/t/p/w185" + posterPath)
                        .into(imgPosterFavorite);
                tvTitleFavorite.setText(title);
                btnReleaseDateFavorite.setText(releaseDate);
                tvOverviewFavorite.setText(overview);

                setActionBar(title);
                break;
        }
    }

    private void setActionBar(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}
