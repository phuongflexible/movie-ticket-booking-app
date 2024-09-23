package com.example.bookticketapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.dao.RoomQuery;
import com.example.bookticketapp.dao.SeatQuery;
import com.example.bookticketapp.events.SeatSelectListener;
import com.example.bookticketapp.models.Room;
import com.example.bookticketapp.models.Seat;

import java.util.List;

public class SeatListViewAdapter extends BaseAdapter {
    private List<Seat> listSeats;
    private Context context;
    private LayoutInflater inflater;
    private RoomQuery roomQuery;
    private SeatSelectListener listener;

    public SeatListViewAdapter(List<Seat> listSeats, Context context, SeatSelectListener listener) {
        this.listSeats = listSeats;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.roomQuery = new RoomQuery(context);
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return listSeats.size();
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
        view = this.inflater.inflate(R.layout.item_seat, null);
        TextView seatNumber = view.findViewById(R.id.seatNumber);
        seatNumber.setText(listSeats.get(i).getSeatNumber());

        TextView roomName = view.findViewById(R.id.roomName);
        Room room = roomQuery.getRoomById(listSeats.get(i).getRoomId());
        roomName.setText(room.getName());

        CheckBox seatAvailable = view.findViewById(R.id.seatAvailable);
        if (listSeats.get(i).isAvailable())
        {
            seatAvailable.setChecked(true);
        }
        else
        {
            seatAvailable.setChecked(false);
        }

        Button btnUpdateSeat = view.findViewById(R.id.btnUpdateSeat);
        btnUpdateSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updateSeat(listSeats.get(i));
            }
        });


        Button btnDeleteSeat = view.findViewById(R.id.btnDeleteSeat);
        btnDeleteSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.deleteSeat(listSeats.get(i));
            }
        });
        return view;
    }
}
