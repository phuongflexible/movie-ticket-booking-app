package com.example.bookticketapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.dao.CinemaQuery;
import com.example.bookticketapp.events.RoomSelectListener;
import com.example.bookticketapp.models.Cinema;
import com.example.bookticketapp.models.Room;

import java.util.List;

public class RoomAdapter extends BaseAdapter {
    private List<Room> listRooms;
    private Context context;
    private LayoutInflater inflater;
    private CinemaQuery cinemaQuery;
    private RoomSelectListener listener;

    public RoomAdapter(List<Room> listRooms, Context context, RoomSelectListener listener) {
        this.listRooms = listRooms;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.cinemaQuery = new CinemaQuery(context);
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return listRooms.size();
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
        view = this.inflater.inflate(R.layout.item_room, null);
        TextView roomName = view.findViewById(R.id.roomName);
        TextView cinemaName = view.findViewById(R.id.cinemaName);
        roomName.setText(listRooms.get(i).getName());
        Cinema cinema = cinemaQuery.getCinemaById(listRooms.get(i).getCinemaId());
        cinemaName.setText(cinema.getName());

        Button btnUpdateRoom = view.findViewById(R.id.btnUpdateRoom);
        btnUpdateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updateRoom(listRooms.get(i));
            }
        });

        Button btnDeleteRoom = view.findViewById(R.id.btnDeleteRoom);
        btnDeleteRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.deleteRoom(listRooms.get(i));
            }
        });
        return view;
    }
}
