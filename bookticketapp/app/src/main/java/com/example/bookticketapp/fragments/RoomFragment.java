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
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.activities.AddRoomActivity;
import com.example.bookticketapp.activities.UpdateRoomActivity;
import com.example.bookticketapp.adapters.RoomAdapter;
import com.example.bookticketapp.dao.RoomQuery;
import com.example.bookticketapp.events.RoomSelectListener;
import com.example.bookticketapp.models.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomFragment extends Fragment implements RoomSelectListener {
    Activity context;
    ListView lvRoom;
    Button btnAddRoom;
    RoomQuery roomQuery;
    RoomAdapter roomAdapter;
    List<Room> listRooms = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_room, container, false);
        lvRoom = view.findViewById(R.id.lvRoom);
        btnAddRoom = view.findViewById(R.id.btnAddRoom);
        roomQuery = new RoomQuery(context);
        listRooms = roomQuery.getAllRooms();
        roomAdapter = new RoomAdapter(listRooms, context, this);
        lvRoom.setAdapter(roomAdapter);

        btnAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddRoomActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void updateRoom(Room room) {
        int id = room.getId();
        String name = room.getName();
        int cinemaId = room.getCinemaId();
        Intent intent = new Intent(context, UpdateRoomActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("cinemaId", cinemaId);
        startActivity(intent);
    }

    @Override
    public void deleteRoom(Room room) {
        Boolean delete = roomQuery.deleteRoom(room.getId());
        if (delete == true)
        {
            Toast.makeText(context, "Xoá phòng thành công", Toast.LENGTH_SHORT).show();
            reloadRoomFragment();
        }
        else
        {
            Toast.makeText(context, "Xoá phòng thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void reloadRoomFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new RoomFragment());
        fragmentTransaction.commit();
    }
}