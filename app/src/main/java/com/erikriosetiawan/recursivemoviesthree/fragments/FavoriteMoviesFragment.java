package com.erikriosetiawan.recursivemoviesthree.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erikriosetiawan.recursivemoviesthree.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMoviesFragment extends Fragment {


    public FavoriteMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movies, container, false);
    }

}
