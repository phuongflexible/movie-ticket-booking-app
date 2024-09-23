package com.example.bookticketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.dao.CinemaQuery;
import com.example.bookticketapp.dao.RoomQuery;
import com.example.bookticketapp.models.Cinema;
import com.example.bookticketapp.models.Room;

import java.util.ArrayList;
import java.util.List;

public class AddRoomActivity extends AppCompatActivity {

    EditText editAddRoomName;
    Spinner spnCinema;
    Button btnConfirmAddRoom;
    List<String> listCinemas = new ArrayList<>();
    CinemaQuery cinemaQuery;
    RoomQuery roomQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        editAddRoomName = findViewById(R.id.editAddRoomName);
        spnCinema = findViewById(R.id.spnCinema);
        btnConfirmAddRoom = findViewById(R.id.btnConfirmAddRoom);

        cinemaQuery = new CinemaQuery(this);
        listCinemas = cinemaQuery.getCinemaNames();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.item_spinner, listCinemas);
        spnCinema.setAdapter(arrayAdapter);

        roomQuery = new RoomQuery(this);

        btnConfirmAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = editAddRoomName.getText().toString().trim();
                int cinemaId = (int) spnCinema.getSelectedItemId() + 1;
                if (!newName.equals(""))
                {
                    Room room = new Room(newName, cinemaId);
                    Boolean insert = roomQuery.addRoom(room);
                    if (insert == true)
                    {
                        Toast.makeText(AddRoomActivity.this, "Thêm phòng thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddRoomActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(AddRoomActivity.this, "Thêm phòng thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(AddRoomActivity.this, "Không thêm được do thiếu thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}