package com.example.bookticketapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.models.Seat;

import java.util.List;

public class SeatGridViewAdapter extends BaseAdapter {
    private Context context;
    private int layoutItem;
    private List<Seat> seatList;
    private boolean[] selectedArray;

    public SeatGridViewAdapter(Context context, int layoutItem, List<Seat> seatList) {
        this.context = context;
        this.layoutItem = layoutItem;
        this.seatList = seatList;
        this.selectedArray = new boolean[seatList.size()];
    }

    @Override
    public int getCount() {
        return seatList.size();
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layoutItem, null);

        Seat seat = seatList.get(i);
        Button btnItemSeat = view.findViewById(R.id.btnItemSeat);

        btnItemSeat.setText(seat.getSeatNumber());
//---------------------------
        if (seat.isAvailable()) {
            btnItemSeat.setBackgroundColor(Color.parseColor("#BDD8CF")); // Ghế trống
        } else {
            btnItemSeat.setBackgroundColor(Color.parseColor("#7B8283")); // Ghế đã bán
        }

        btnItemSeat.setOnClickListener(v -> {
            if (seat.isAvailable()) {
                if (selectedArray[i] == false) {
                    Toast.makeText(context, "Choose " + seat.getSeatNumber(), Toast.LENGTH_SHORT).show();
                    selectedArray[i] = true;
                    btnItemSeat.setBackgroundColor(Color.GREEN); // Ghế đang chọn
                } else {
                    selectedArray[i] = false;
                    btnItemSeat.setBackgroundColor(Color.parseColor("#BDD8CF"));
                }
            }
        });
//--------------------------
        return view;
    }
}
