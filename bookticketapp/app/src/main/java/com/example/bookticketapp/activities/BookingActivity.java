package com.example.bookticketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.adapters.SeatGridViewAdapter;
import com.example.bookticketapp.models.Seat;

import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity {
    private GridView gvSeats;
    private SeatGridViewAdapter seatAdapter;
    private List<Seat> seatList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        initSeats();
        findViewByIds();

        seatAdapter = new SeatGridViewAdapter(this, R.layout.item_button_seat, seatList);
        gvSeats.setAdapter(seatAdapter);

    }

    private void findViewByIds() {
        gvSeats = findViewById(R.id.gvSeats);
    }

    private void initSeats() {
        seatList = new ArrayList<>();
        seatList.add(new Seat("A1", true));
        seatList.add(new Seat("A2", false));
        seatList.add(new Seat("A3", false));
        seatList.add(new Seat("A4", false));
        seatList.add(new Seat("A5", true));
        seatList.add(new Seat("A6", true));
        seatList.add(new Seat("A7", false));
        seatList.add(new Seat("A1", true));
        seatList.add(new Seat("A2", false));
        seatList.add(new Seat("A3", false));
        seatList.add(new Seat("A4", false));
        seatList.add(new Seat("A5", true));
        seatList.add(new Seat("A6", true));
        seatList.add(new Seat("A7", false));
        seatList.add(new Seat("A1", true));
        seatList.add(new Seat("A2", false));
        seatList.add(new Seat("A3", false));
        seatList.add(new Seat("A4", false));
        seatList.add(new Seat("A5", true));
        seatList.add(new Seat("A6", true));
        seatList.add(new Seat("A7", false));
        seatList.add(new Seat("A1", true));
        seatList.add(new Seat("A2", false));
        seatList.add(new Seat("A3", false));
        seatList.add(new Seat("A4", false));
        seatList.add(new Seat("A5", true));
        seatList.add(new Seat("A6", true));
        seatList.add(new Seat("A7", false));
    }
}