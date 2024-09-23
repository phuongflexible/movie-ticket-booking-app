package com.example.bookticketapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.events.LocationSelectListener;
import com.example.bookticketapp.models.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends BaseAdapter {
    private List<Location> listLocations;
    private Context context;
    private LayoutInflater inflater;
    private LocationSelectListener listener;

    public LocationAdapter(List<Location> listLocations, Context context, LocationSelectListener listener) {
        this.listLocations = listLocations;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return listLocations.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = this.inflater.inflate(R.layout.item_location,null);
        TextView listItemLocation = view.findViewById(R.id.listItemLocation);
        listItemLocation.setText(listLocations.get(i).getName());

        Button btnUpdateLocation = view.findViewById(R.id.btnUpdateLocation);
        btnUpdateLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updateLocation(listLocations.get(i));
            }
        });

        Button btnDeleteLocation = view.findViewById(R.id.btnDeleteLocation);
        btnDeleteLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.deleteLocation(listLocations.get(i));
            }
        });
        return view;
    }
}
