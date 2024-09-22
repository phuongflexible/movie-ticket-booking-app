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
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.activities.AddLocationActivity;
import com.example.bookticketapp.adapters.LocationAdapter;
import com.example.bookticketapp.dao.LocationQuery;
import com.example.bookticketapp.models.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationFragment extends Fragment {
    Activity context;
    LocationQuery locationQuery;
    List<Location> listLocations = new ArrayList<>();
    ListView lvLocation;
    LocationAdapter locationAdapter;
    Button btnAddLocation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        locationQuery = new LocationQuery(context);
        listLocations = locationQuery.getAllLocation();
        lvLocation = view.findViewById(R.id.lvLocation);
        locationAdapter = new LocationAdapter(listLocations, context);
        lvLocation.setAdapter(locationAdapter);

        btnAddLocation = view.findViewById(R.id.btnAddLocation);

        btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddLocationActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}