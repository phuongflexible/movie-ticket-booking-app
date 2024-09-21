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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment implements MovieSelectListener {
    Activity context;
    Button btnAddFilm;
    MovieQuery movieQuery;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieFragment newInstance(String param1, String param2) {
        MovieFragment fragment = new MovieFragment();
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