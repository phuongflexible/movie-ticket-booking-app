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
import com.example.bookticketapp.activities.AddLocationActivity;
import com.example.bookticketapp.activities.UpdateLocationActivity;
import com.example.bookticketapp.adapters.LocationAdapter;
import com.example.bookticketapp.dao.LocationQuery;
import com.example.bookticketapp.events.LocationSelectListener;
import com.example.bookticketapp.models.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationFragment extends Fragment implements LocationSelectListener {
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
        locationAdapter = new LocationAdapter(listLocations, context, this);
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

    @Override
    public void updateLocation(Location location) {
        int id = location.getId();
        String name = location.getName();
        Intent intent = new Intent(context, UpdateLocationActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    @Override
    public void deleteLocation(Location location) {
        Boolean delete = locationQuery.deleteLocation(location.getId());
        if (delete == true)
        {
            Toast.makeText(context, "Xoá địa điểm thành công", Toast.LENGTH_SHORT).show();
            reloadLocationFragment();
        }
        else
        {
            Toast.makeText(context, "Xoá địa điểm thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void reloadLocationFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new LocationFragment());
        fragmentTransaction.commit();
    }
}