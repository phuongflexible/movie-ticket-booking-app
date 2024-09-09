package com.example.bookticketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;

import com.example.bookticketapp.R;
import com.example.bookticketapp.adapters.SeatGridViewAdapter;
import com.example.bookticketapp.models.PaymentMethod;
import com.example.bookticketapp.models.Seat;

import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity {
    private GridView gvSeats;
    private Spinner spnMethod;
    private SeatGridViewAdapter seatAdapter;
    private ArrayAdapter methodSpinnerAdapter;
    private List<Seat> seatList;
    private List<PaymentMethod> paymentMethodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        initSeats();
        initPaymentMethod();
        findViewByIds();

        seatAdapter = new SeatGridViewAdapter(this, R.layout.item_button_seat, seatList);
        gvSeats.setAdapter(seatAdapter);

        methodSpinnerAdapter = new ArrayAdapter(this, R.layout.item_method_spinner, paymentMethodList);
        spnMethod.setAdapter(methodSpinnerAdapter);

    }

    private void findViewByIds() {
        gvSeats = findViewById(R.id.gvSeats);
        spnMethod = findViewById(R.id.spinnerPaymentMethod);
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

    private void initPaymentMethod() {
        paymentMethodList = new ArrayList<>();
        paymentMethodList.add(new PaymentMethod(1, "Tiền Mặt"));
        paymentMethodList.add(new PaymentMethod(1, "Momo"));
        paymentMethodList.add(new PaymentMethod(1, "Zalo Pay"));
    }
}