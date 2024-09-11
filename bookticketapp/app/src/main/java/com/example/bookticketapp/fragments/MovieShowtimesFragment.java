package com.example.bookticketapp.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import com.example.bookticketapp.adapters.LocationSpinnerAdapter;
import com.example.bookticketapp.adapters.ShowtimesExpandListAdapter;
import com.example.bookticketapp.dao.CinemaQuery;
import com.example.bookticketapp.dao.LocationQuery;
import com.example.bookticketapp.dao.ShowtimeQuery;
import com.example.bookticketapp.models.Cinema;
import com.example.bookticketapp.models.Location;
import com.example.bookticketapp.models.Movie;
import com.example.bookticketapp.models.Showtime;
import com.example.bookticketapp.utils.DatetimeUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MovieShowtimesFragment extends Fragment {
    private Spinner spnLocation;
    private LinearLayout linearButtons;
    private ExpandableListView expandList;
    private LocationSpinnerAdapter locationAdapter;
    private ShowtimesExpandListAdapter showtimesAdapter;
    private List<Cinema> cinemaList;
    private HashMap<Cinema, List<Showtime>> showtimeMap;
    private CinemaQuery cinemaQuery;
    private ShowtimeQuery showtimeQuery;
    private Movie movie;
    private String selectedDate;
    private Button selectedButton = null;

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
        initData();
        initLocation();
        initShowtime();
        addDateButtons(linearButtons);

        return view;
    }

    private void findViewByIds(View view) {
        expandList = view.findViewById(R.id.expandListShowtimes);
        spnLocation = view.findViewById(R.id.spnLocation);
        linearButtons = view.findViewById(R.id.linearButtons);
    }

    private void initData() {
        if (getArguments() != null) {
            movie = (Movie) getArguments().getSerializable("movie");
        }
        cinemaQuery = new CinemaQuery(getContext());
        showtimeQuery = new ShowtimeQuery(getContext());
        showtimeMap = new HashMap<>();
        selectedDate = DatetimeUtils.dateToString(Calendar.getInstance());   //mặc định là ngày hiện tại
    }

    private void initLocation() {
        locationAdapter = new LocationSpinnerAdapter(getContext());
        spnLocation.setAdapter(locationAdapter);

        spnLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Location location = (Location) adapterView.getItemAtPosition(i);
                updateShowtimes(location);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initShowtime() {
        cinemaList = new ArrayList<>();
        showtimesAdapter = new ShowtimesExpandListAdapter(getContext(), cinemaList, showtimeMap);
        expandList.setAdapter(showtimesAdapter);
    }

    private void addDateButtons(LinearLayout linearButtons) {
        SimpleDateFormat sdf = new SimpleDateFormat("E d/M", new Locale("vi", "VN"));
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < 11; i++) {
            Button b = new Button(getContext());

            String formattedDate = sdf.format(calendar.getTime());
            b.setText(formattedDate);
            b.setTextColor(Color.WHITE);
            b.setBackgroundColor(Color.GRAY); // Màu nền mặc định

            linearButtons.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(12, 12, 12, 12);
            b.setLayoutParams(params);

            linearButtons.addView(b);

            final String buttonDate = DatetimeUtils.dateToString(calendar);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Đổi màu nền cho nút được chọn và khôi phục màu nền của nút trước đó
                    if (selectedButton != null) {
                        selectedButton.setBackgroundColor(Color.GRAY);
                    }

                    b.setBackgroundColor(Color.BLUE);

                    // Cập nhật ngày được chọn
                    selectedDate = buttonDate;
                    updateShowtimes((Location) spnLocation.getSelectedItem());

                    // Cập nhật biến lưu trữ nút được chọn
                    selectedButton = b;
                }
            });

            // Tăng ngày thêm 1
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        // Đặt màu nền cho ngày hiện tại khi tạo
        if (linearButtons.getChildCount() > 0) {
            selectedButton = (Button) linearButtons.getChildAt(0);
            selectedButton.setBackgroundColor(Color.BLUE);
        }
    }

    private void updateShowtimes(Location location) {
        // lấy danh sách rạp theo khu vực
        if (movie != null) {
            if (location.getName().equals("Toàn Quốc")) {          // "Toàn Quốc"
                cinemaList.clear();
                cinemaList.addAll(cinemaQuery.getAllCinemas());
            } else {                                               // Khu vực khác
                cinemaList.clear();
                cinemaList.addAll(cinemaQuery.getCinemasByLocationId(location.getId()));
            }

            // lọc các rạp có suất chiếu trong ngày được chọn
            List<Cinema> filteredCinemas = new ArrayList<>();
            for (Cinema cinema : cinemaList) {
                List<Showtime> showtimeList = showtimeQuery
                        .getShowtimesByMovieAndCinemaAndDate(movie.getId(), cinema.getId(), selectedDate);
                if (!showtimeList.isEmpty()) {   // nếu list showtime của rạp không trống thì thêm rạp đó vào danh sách lọc
                    filteredCinemas.add(cinema);
                    showtimeMap.put(cinema, showtimeList);
                }
            }

            cinemaList.clear();
            cinemaList.addAll(filteredCinemas);
            showtimesAdapter.notifyDataSetChanged();
        }
    }
}
