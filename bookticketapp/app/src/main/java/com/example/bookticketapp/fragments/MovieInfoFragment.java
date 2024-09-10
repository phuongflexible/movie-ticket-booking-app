package com.example.bookticketapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookticketapp.R;
import com.example.bookticketapp.models.Movie;

import java.io.Serializable;

public class MovieInfoFragment extends Fragment {
    private Movie movie;
    private TextView txtInfo;

    public static MovieInfoFragment newInstance(Movie movie) {
        MovieInfoFragment fragment = new MovieInfoFragment();
        Bundle bundle = new Bundle();

        bundle.putSerializable("movie", movie);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_info, container, false);

        if (getArguments() != null) {
            movie = (Movie) getArguments().getSerializable("movie");

            txtInfo = view.findViewById(R.id.txtMovieInfo);
            txtInfo.setText(movie.getDesciption());
        }

        return view;
    }
}
