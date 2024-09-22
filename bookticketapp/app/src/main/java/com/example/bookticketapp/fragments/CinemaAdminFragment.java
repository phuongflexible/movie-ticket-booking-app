package com.example.bookticketapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.activities.AddCinemaActivity;
import com.example.bookticketapp.adapters.CinemaListViewAdapter;
import com.example.bookticketapp.dao.CinemaQuery;
import com.example.bookticketapp.models.Cinema;

import java.util.ArrayList;
import java.util.List;

public class CinemaAdminFragment extends Fragment {

    Activity context;
    CinemaQuery cinemaQuery;
    List<Cinema> listCinemas;
    CinemaListViewAdapter cinemaAdapter;
    ListView lvCinemas;
    Button btnAddCinema;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_cinema_admin, container, false);

        cinemaQuery = new CinemaQuery(context);
        listCinemas = new ArrayList<>();
        listCinemas = cinemaQuery.getAllCinemas();
        cinemaAdapter = new CinemaListViewAdapter(context, R.layout.item_cinema_admin, listCinemas);

        lvCinemas = view.findViewById(R.id.listViewCinema);
        lvCinemas.setAdapter(cinemaAdapter);

        btnAddCinema= view.findViewById(R.id.btnAddCinema);

        btnAddCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddCinemaActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


}