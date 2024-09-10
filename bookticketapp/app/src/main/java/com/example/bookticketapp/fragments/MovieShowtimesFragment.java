package com.example.bookticketapp.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookticketapp.R;
import com.example.bookticketapp.activities.BookingActivity;
import com.example.bookticketapp.adapters.ShowtimesExpandListAdapter;
import com.example.bookticketapp.dao.CinemaQuery;
import com.example.bookticketapp.dao.LocationQuery;
import com.example.bookticketapp.models.Cinema;
import com.example.bookticketapp.models.Location;
import com.example.bookticketapp.models.Movie;
import com.example.bookticketapp.models.Showtime;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MovieShowtimesFragment extends Fragment {
    private ExpandableListView expandList;
    private ShowtimesExpandListAdapter showtimesAdapter;
    private List<Cinema> cinemaList;
    private Spinner spnLocation;
    private Movie movie;
    private CinemaQuery cinemaQuery;
    private LinearLayout linearButtons;

    public static MovieShowtimesFragment newInstance(Movie movie) {
        MovieShowtimesFragment fragment = new MovieShowtimesFragment();
        Bundle bundle = new Bundle();

        bundle.putSerializable("movie", movie);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_showtimes, container, false);

        findViewByIds(view);

        initLocation();

        addDateButtons(linearButtons);



        spnLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cinemaQuery = new CinemaQuery(getContext());

                // ExpandableListView lịch chiếu phim
                cinemaList = cinemaQuery.getCinemasByLocationId(i);
                showtimesAdapter = new ShowtimesExpandListAdapter(getContext(), cinemaList);
                expandList.setAdapter(showtimesAdapter);

                expandList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                        Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), BookingActivity.class);
                        startActivity(intent);

                        return true;
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    private void findViewByIds(View view) {
        expandList = view.findViewById(R.id.expandListShowtimes);
        spnLocation = view.findViewById(R.id.spnLocation);
        linearButtons = view.findViewById(R.id.linearButtons);
    }

//    private List<Cinema> getFakeData() {
//        List<Cinema> newCinemas = new ArrayList<>();
//
//        List<Showtime> showtimes11 = Arrays.asList(new Showtime("10:00 AM"), new Showtime("12:00 PM"));
//        List<Showtime> showtimes22 = Arrays.asList(new Showtime("11:00 AM"), new Showtime("02:00 PM"));
//
//        newCinemas.add(new Cinema("Galaxy Nguyễn Du", showtimes11));
//        newCinemas.add(new Cinema("CGV Đồng Khởi", showtimes22));
//
//        return newCinemas;
//    }

    private void initLocation() {
        LocationQuery locationQuery = new LocationQuery(getContext());
        List<Location> locationList = locationQuery.getAllLocation();   // lấy danh sách địa điểm
        List<String> locationNames = new ArrayList<>();                 // lấy danh sách tên địa điểm

        for (Location l : locationList) {
            locationNames.add(l.getName());
        }

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, locationNames);
        spnLocation.setAdapter(locationAdapter);
    }

    private void addDateButtons(LinearLayout linearButtons) {
        SimpleDateFormat sdf = new SimpleDateFormat("E d/M", new Locale("vi", "VN"));
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < 11; i++) {
            Button b = new Button(getContext());

            String formattedDate = sdf.format(calendar.getTime());
            b.setText(formattedDate);
            b.setTextColor(Color.BLACK);

            linearButtons.addView(b);

            // cộng 1 ngày
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
    }
}
