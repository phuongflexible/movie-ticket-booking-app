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
import com.example.bookticketapp.activities.AddSeatActivity;
import com.example.bookticketapp.activities.UpdateLocationActivity;
import com.example.bookticketapp.activities.UpdateSeatActivity;
import com.example.bookticketapp.adapters.SeatListViewAdapter;
import com.example.bookticketapp.dao.SeatQuery;
import com.example.bookticketapp.events.SeatSelectListener;
import com.example.bookticketapp.models.Seat;

import java.util.ArrayList;
import java.util.List;

public class SeatFragment extends Fragment implements SeatSelectListener {

    Activity context;
    SeatListViewAdapter seatAdapter;
    ListView lvSeat;
    Button btnAddSeat;
    SeatQuery seatQuery;
    List<Seat> listSeats = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_seat, container, false);
        lvSeat = view.findViewById(R.id.lvSeat);
        btnAddSeat = view.findViewById(R.id.btnAddSeat);

        seatQuery = new SeatQuery(context);
        listSeats = seatQuery.getAllSeats();
        seatAdapter = new SeatListViewAdapter(listSeats, context, this);
        lvSeat.setAdapter(seatAdapter);

        btnAddSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddSeatActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void updateSeat(Seat seat) {
        int id = seat.getId();
        String number = seat.getSeatNumber();
        int roomId = seat.getRoomId();
        Boolean isAvailable = seat.isAvailable();
        Intent intent = new Intent(context, UpdateSeatActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("number", number);
        intent.putExtra("roomId", roomId);
        intent.putExtra("isAvailable", isAvailable);
        startActivity(intent);
    }

    @Override
    public void deleteSeat(Seat seat) {
        Boolean delete = seatQuery.deleteSeat(seat.getId());
        if (delete == true)
        {
            Toast.makeText(context, "Xoá ghế thành công", Toast.LENGTH_SHORT).show();
            reloadSeatFragment();
        }
        else
        {
            Toast.makeText(context, "Xoá ghế thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void reloadSeatFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new SeatFragment());
        fragmentTransaction.commit();
    }
}