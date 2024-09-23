package com.example.bookticketapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.activities.AddCinemaActivity;
import com.example.bookticketapp.activities.UpdateCinemaActivity;
import com.example.bookticketapp.adapters.CinemaListViewAdapter;
import com.example.bookticketapp.dao.CinemaQuery;
import com.example.bookticketapp.events.CinemaSelectListener;
import com.example.bookticketapp.models.Cinema;

import java.util.ArrayList;
import java.util.List;

public class CinemaAdminFragment extends Fragment implements CinemaSelectListener {

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
        cinemaAdapter = new CinemaListViewAdapter(context, R.layout.item_cinema_admin, listCinemas, this);

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


    @Override
    public void updateCinema(Cinema cinema) {
        int id = cinema.getId();
        String name = cinema.getName();
        String address = cinema.getAddress();
        byte[] image = cinema.getImage();
        int locationId = cinema.getLocationId();
        Intent intent = new Intent(context, UpdateCinemaActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("address", address);
        startActivity(intent);
    }

    @Override
    public void deleteCinema(Cinema cinema) {
        Boolean result = cinemaQuery.deleteCinema(cinema.getId());
        if (result == true)
        {
            Toast.makeText(context, "Xóa rạp phim thành công", Toast.LENGTH_SHORT).show();
            reloadCinemaFragment();
        }
        else
        {
            Toast.makeText(context, "Xóa rạp phim thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void reloadCinemaFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new CategoryFragment());
        fragmentTransaction.commit();
    }
}