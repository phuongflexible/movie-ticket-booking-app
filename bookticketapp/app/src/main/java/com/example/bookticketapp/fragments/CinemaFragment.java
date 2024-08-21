package com.example.bookticketapp.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.bookticketapp.R;
import com.example.bookticketapp.adapters.CinemaAdapter;
import com.example.bookticketapp.models.Cinema;
import java.util.ArrayList;
import java.util.List;

public class CinemaFragment extends Fragment {
    // Location
    private Spinner spinnerLocation;
    private ArrayAdapter arrayAdapter;
    private List<String> locationArray;
    // Cinema
    private ListView lvCinema;
    private CinemaAdapter cinemaAdapter;
    private List<Cinema> cinemaArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cinema, container, false);

        findViewByIds(view);

        locationArray = new ArrayList<String>();
        cinemaArray = new ArrayList<Cinema>();
        initLocation();
        initCinema();

        // Location
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, locationArray);
        spinnerLocation.setAdapter(arrayAdapter);

        // Cinema
        cinemaAdapter = new CinemaAdapter(getContext(), R.layout.item_cinema, cinemaArray);
        lvCinema.setAdapter(cinemaAdapter);

        return view;
    }

    private void findViewByIds(View view) {
        spinnerLocation = view.findViewById(R.id.spinnerLocation);
        lvCinema = view.findViewById(R.id.lvCinema);
    }

    private void initLocation() {
        locationArray.add("Toàn quốc");
        locationArray.add("Tp HCM");
        locationArray.add("Hà Nội");
        locationArray.add("Nha Trang");
        locationArray.add("Cần Thơ");
        locationArray.add("Đồng Nai");
        locationArray.add("Long An");
    }

    private void initCinema() {
        cinemaArray.add(new Cinema("Galaxy Nguyễn Du", "đây là địa chỉ", R.drawable.cinema1));
        cinemaArray.add(new Cinema("CineStar Hai Bà Trưng", "dsajdhas Quận 1, Tp.HCM", R.drawable.cinema2));
        cinemaArray.add(new Cinema("CGV Đồng Khởi", "ndashdai Quận 1, Tp.HCM", R.drawable.cinema3));
        cinemaArray.add(new Cinema("Galaxy Nguyễn Du", "đây là địa chỉ", R.drawable.cinema1));
        cinemaArray.add(new Cinema("CineStar Hai Bà Trưng", "dsajdhas Quận 1, Tp.HCM", R.drawable.cinema2));
        cinemaArray.add(new Cinema("CGV Đồng Khởi", "ndashdai Quận 1, Tp.HCM", R.drawable.cinema3));
        cinemaArray.add(new Cinema("Galaxy Nguyễn Du", "đây là địa chỉ", R.drawable.cinema1));
        cinemaArray.add(new Cinema("CineStar Hai Bà Trưng", "dsajdhas Quận 1, Tp.HCM", R.drawable.cinema2));
        cinemaArray.add(new Cinema("CGV Đồng Khởi", "ndashdai Quận 1, Tp.HCM", R.drawable.cinema3));
    }
}