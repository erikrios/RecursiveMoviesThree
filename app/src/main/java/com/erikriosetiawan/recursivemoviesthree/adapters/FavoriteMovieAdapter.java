package com.erikriosetiawan.recursivemoviesthree.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erikriosetiawan.recursivemoviesthree.FavoriteDetailsActivity;
import com.erikriosetiawan.recursivemoviesthree.R;
import com.erikriosetiawan.recursivemoviesthree.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Movie> movies;

    public FavoriteMovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public FavoriteMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_favorite, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tvMovieTitle.setText(getMovies().get(i).getTitle());
        viewHolder.tvReleaseDate.setText(getMovies().get(i).getReleaseDate());
        viewHolder.tvOverview.setText(getMovies().get(i).getOverview());

        Picasso.get()
                .load("https://image.tmdb.org/t/p/w185" + getMovies().get(i).getPosterPath())
                .into(viewHolder.imgViewPoster);

        viewHolder.itemView.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent dataIntent = new Intent(context, FavoriteDetailsActivity.class);
                dataIntent.putExtra(FavoriteDetailsActivity.FAVORITE_MOVIE_POSTER_KEY, getMovies().get(position).getPosterPath());
                dataIntent.putExtra(FavoriteDetailsActivity.FAVORITE_MOVIE_TITLE_KEY, getMovies().get(position).getTitle());
                dataIntent.putExtra(FavoriteDetailsActivity.FAVORITE_MOVIE_RELEASE_DATE_KEY, getMovies().get(position).getReleaseDate());
                dataIntent.putExtra(FavoriteDetailsActivity.FAVORITE_MOVIE_OVERVIEW_KEY, getMovies().get(position).getOverview());
                dataIntent.putExtra(FavoriteDetailsActivity.FAVORITE_KEY, FavoriteDetailsActivity.FAVORITE_MOVIE_KEY);
                context.startActivity(dataIntent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMovieTitle, tvReleaseDate, tvOverview;
        ImageView imgViewPoster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tv_list_title_favorite);
            tvReleaseDate = itemView.findViewById(R.id.btn_list_release_date_favorite);
            tvOverview = itemView.findViewById(R.id.tv_list_overview_favorite);
            imgViewPoster = itemView.findViewById(R.id.img_list_poster_favorite);
        }
    }
}
