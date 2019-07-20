package com.erikriosetiawan.recursivemoviesthree.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erikriosetiawan.recursivemoviesthree.R;
import com.erikriosetiawan.recursivemoviesthree.models.TvShow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteTvShowAdapter extends RecyclerView.Adapter<FavoriteTvShowAdapter.ViewHolder> {

    private Context context;
    private ArrayList<TvShow> tvShows;

    public FavoriteTvShowAdapter(Context context, ArrayList<TvShow> tvShows) {
        this.context = context;
        this.tvShows = tvShows;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<TvShow> getTvShows() {
        return tvShows;
    }

    public void setTvShows(ArrayList<TvShow> tvShows) {
        this.tvShows = tvShows;
    }

    @NonNull
    @Override
    public FavoriteTvShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_favorite, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTvShowAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tvTvShowTitle.setText(getTvShows().get(i).getName());
        viewHolder.tvTvShowReleaseDate.setText(getTvShows().get(i).getFirstAirDate());
        viewHolder.tvTvShowOverview.setText(getTvShows().get(i).getOverview());

        Picasso.get()
                .load("https://image.tmdb.org/t/p/w185" + getTvShows().get(i).getPosterPath())
                .into(viewHolder.imgViewPoster);
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTvShowTitle, tvTvShowReleaseDate, tvTvShowOverview;
        ImageView imgViewPoster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTvShowTitle = itemView.findViewById(R.id.tv_list_title_favorite);
            tvTvShowReleaseDate = itemView.findViewById(R.id.btn_list_release_date_favorite);
            tvTvShowOverview = itemView.findViewById(R.id.tv_list_overview_favorite);
            imgViewPoster = itemView.findViewById(R.id.img_list_poster_favorite);
        }
    }
}