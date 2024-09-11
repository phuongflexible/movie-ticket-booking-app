package com.example.bookticketapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.activities.AddMovieActivity;
import com.example.bookticketapp.adapters.FilmAdapter;
import com.example.bookticketapp.dao.MovieQuery;
import com.example.bookticketapp.models.Movie;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmFragment extends Fragment {
    Activity context;
    Button btnAddFilm;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FilmFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilmFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilmFragment newInstance(String param1, String param2) {
        FilmFragment fragment = new FilmFragment();
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
        return inflater.inflate(R.layout.fragment_film, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try
        {
            MovieQuery movieQuery = new MovieQuery(context);
            ArrayList<Movie> movieArrayList = movieQuery.readMovies();
            FilmAdapter filmAdapter = new FilmAdapter(movieArrayList, context);
            RecyclerView filmRV = view.findViewById(R.id.viewFilms);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            filmRV.setLayoutManager(linearLayoutManager);
            filmRV.setAdapter(filmAdapter);
            //Toast.makeText(context, "List:" + movieArrayList.get(0).getCategory().getName(), Toast.LENGTH_SHORT).show();
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
}