package com.example.bookticketapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookticketapp.R;
import com.example.bookticketapp.adapters.ExpandListShowtimesAdapter;
import com.example.bookticketapp.models.Cinema;
import com.example.bookticketapp.models.Showtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieShowtimesFragment extends Fragment {
    private ExpandableListView expandList;
    private ExpandListShowtimesAdapter showtimesAdapter;
    private List<Cinema> cinemaList;
    private Spinner spnLocation;
    private ArrayAdapter arrayAdapter;
    private List<String> locationArray;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_showtimes, container, false);

        FindViewByIds(view);

        // ExpandableListView lịch chiếu phim
        cinemaList = getFakeData();
        showtimesAdapter = new ExpandListShowtimesAdapter(getContext(), cinemaList);
        expandList.setAdapter(showtimesAdapter);

        // Spinner khu vực
        locationArray = new ArrayList<String>();
        InitLocation();

        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, locationArray);
        spnLocation.setAdapter(arrayAdapter);

        return view;
    }

    private void FindViewByIds(View view) {
        expandList = view.findViewById(R.id.expandListShowtimes);
        spnLocation = view.findViewById(R.id.spnLocation);
    }

    private List<Cinema> getFakeData() {
        List<Cinema> newCinemas = new ArrayList<>();

        List<Showtime> showtimes11 = Arrays.asList(new Showtime("10:00 AM"), new Showtime("12:00 PM"));
        List<Showtime> showtimes22 = Arrays.asList(new Showtime("11:00 AM"), new Showtime("02:00 PM"));

        newCinemas.add(new Cinema("Galaxy Nguyễn Du", showtimes11));
        newCinemas.add(new Cinema("CGV Đồng Khởi", showtimes22));

        return newCinemas;
    }

    public void InitLocation() {
        locationArray.add("Toàn quốc");
        locationArray.add("Tp HCM");
        locationArray.add("Hà Nội");
        locationArray.add("Nha Trang");
        locationArray.add("Cần Thơ");
        locationArray.add("Đồng Nai");
        locationArray.add("Long An");
    }
}
