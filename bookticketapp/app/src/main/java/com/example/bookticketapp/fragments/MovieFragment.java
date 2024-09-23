package com.example.bookticketapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.activities.AddMovieActivity;
import com.example.bookticketapp.activities.UpdateMovieActivity;
import com.example.bookticketapp.adapters.MovieAdapter;
import com.example.bookticketapp.dao.MovieQuery;
import com.example.bookticketapp.events.MovieSelectListener;
import com.example.bookticketapp.models.Movie;
import com.example.bookticketapp.utils.DatetimeUtils;
import com.example.bookticketapp.utils.ImageUtils;

import java.util.ArrayList;
import java.util.Calendar;

public class MovieFragment extends Fragment implements MovieSelectListener {
    Activity context;
    Button btnAddFilm;
    MovieQuery movieQuery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movieQuery = new MovieQuery(context);
        try
        {
            ArrayList<Movie> movieArrayList = movieQuery.readMovies();
            MovieAdapter movieAdapter = new MovieAdapter(movieArrayList, context, this);
            RecyclerView filmRV = view.findViewById(R.id.viewFilms);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            filmRV.setLayoutManager(linearLayoutManager);
            filmRV.setAdapter(movieAdapter);
        }
        catch (Exception e)
        {
            Toast.makeText(context, "Lỗi khi lấy dữ liệu"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        btnAddFilm = view.findViewById(R.id.btnAddFilm);

        btnAddFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddMovieActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void updateMovie(Movie movie) {
        Integer id = movie.getId();
        String title = movie.getTitle();
        String description = movie.getDesciption();
        int categoryId = movie.getCategoryId();
        int duration = movie.getDuration();
        Calendar openingDay = movie.getOpeningDay();
        float rating = movie.getRating();
        byte[] image = movie.getImage();
        Intent i = new Intent(context, UpdateMovieActivity.class);
        i.putExtra("id", id);
        i.putExtra("title", title);
        i.putExtra("description", description);
        i.putExtra("categoryId", categoryId);
        i.putExtra("duration", duration);
        i.putExtra("openingDay", DatetimeUtils.calendarToString(openingDay));
        i.putExtra("rating", rating);
        startActivity(i);
    }

    @Override
    public void deleteMovie(Movie movie) {
        Boolean result = movieQuery.deleteMovie(movie.getId());
        if (result == true)
        {
            Toast.makeText(context, "Xóa phim thành công", Toast.LENGTH_SHORT).show();
            reloadMovieFragment();
        }
        else
        {
            Toast.makeText(context, "Xóa phim thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void reloadMovieFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new MovieFragment());
        fragmentTransaction.commit();
    }
}