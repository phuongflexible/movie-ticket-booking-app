package com.example.bookticketapp.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.bookticketapp.R;
import com.example.bookticketapp.adapters.CinemaListViewAdapter;
import com.example.bookticketapp.adapters.LocationSpinnerAdapter;
import com.example.bookticketapp.dao.CinemaQuery;
import com.example.bookticketapp.dao.LocationQuery;
import com.example.bookticketapp.models.Cinema;
import com.example.bookticketapp.models.Location;

import java.util.ArrayList;
import java.util.List;

public class CinemaFragment extends Fragment {
    private Spinner spnLocation;
    private ListView lvCinema;
    private LocationSpinnerAdapter locationAdapter;
    private CinemaListViewAdapter cinemaAdapter;
    private List<Cinema> cinemaList;
    private CinemaQuery cinemaQuery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cinema, container, false);

        findViewByIds(view);
        initLocation();
        initCinemas();

        spnLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cinemaQuery = new CinemaQuery(getContext());
                Location selectedLocation = (Location) adapterView.getItemAtPosition(i);

                String locationName = selectedLocation.getName();
                if (locationName.equals("Toàn Quốc")) {         // nếu chọn "Toàn Quốc" thì lấy tất cả rạp
                    cinemaList.clear();
                    cinemaList.addAll(cinemaQuery.getAllCinemas());
                } else {                                        // chọn khu vực khác thì lấy danh sách rạp thuộc khu vực đó
                    cinemaList.clear();
                    cinemaList.addAll(cinemaQuery.getCinemasByLocationId(i+1));
                }

                cinemaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    private void findViewByIds(View view) {
        spnLocation = view.findViewById(R.id.spinnerLocation);
        lvCinema = view.findViewById(R.id.lvCinema);
    }

    private void initLocation() {
        locationAdapter = new LocationSpinnerAdapter(getContext());
        spnLocation.setAdapter(locationAdapter);
    }

    private void initCinemas() {
        cinemaList = new ArrayList<>();

        cinemaAdapter = new CinemaListViewAdapter(getContext(), R.layout.item_cinema, cinemaList);
        lvCinema.setAdapter(cinemaAdapter);
    }
}