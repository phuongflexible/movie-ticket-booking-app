package com.example.bookticketapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookticketapp.dao.LocationQuery;
import com.example.bookticketapp.models.Location;

import java.util.List;

public class LocationSpinnerAdapter extends ArrayAdapter<Location> {
    Context context;
    List<Location> locationList;
    LocationQuery locationQuery;

    public LocationSpinnerAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_spinner_dropdown_item);
        this.context = context;
        this.locationList = loadLocations();
        addLocation("Toàn Quốc", 0);  // thêm location "Toàn Quốc" vào đầu danh sách

        addAll(locationList);
    }

    private List<Location> loadLocations() {
        locationQuery = new LocationQuery(context);
        return locationQuery.getAllLocation();
    }

    private void addLocation(String locationName, int position) {
        Location location = new Location(0, locationName);
        locationList.add(position, location);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    android.R.layout.simple_spinner_item, parent, false);
        }

        TextView txtLocationName = convertView.findViewById(android.R.id.text1);
        txtLocationName.setText(locationList.get(position).getName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        TextView txtLocationName = convertView.findViewById(android.R.id.text1);
        txtLocationName.setText(locationList.get(position).getName());

        return convertView;
    }
}
