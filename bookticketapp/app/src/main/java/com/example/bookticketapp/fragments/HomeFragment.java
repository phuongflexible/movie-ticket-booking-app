package com.example.bookticketapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.activities.MovieDetailsActivity;
import com.example.bookticketapp.adapters.MovieGridviewAdapter;
import com.example.bookticketapp.dao.MovieQuery;
import com.example.bookticketapp.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private GridView gvMovies;
    private MovieGridviewAdapter movieAdapter;
    private List<Movie> movieList;
    private MovieQuery movieQuery;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        findViewByIds(view);

        movieQuery = new MovieQuery(getContext());
        movieList = movieQuery.getAllMovies();
        movieAdapter = new MovieGridviewAdapter(getContext(), movieList);
        gvMovies.setAdapter(movieAdapter);

        return view;
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void findViewByIds(View view) {
        gvMovies = view.findViewById(R.id.gvMovies);
    }
}