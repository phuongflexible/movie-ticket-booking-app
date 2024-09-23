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
import com.example.bookticketapp.models.Room;

import java.util.ArrayList;
import java.util.List;

public class UpdateRoomActivity extends AppCompatActivity {

    EditText editUpdateRoomName;
    Button btnConfirmUpdateRoom;
    Spinner spnCinema;

    List<String> listCinemas = new ArrayList<>();
    CinemaQuery cinemaQuery;
    RoomQuery roomQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_room);

        editUpdateRoomName = findViewById(R.id.editUpdateRoomName);
        spnCinema = findViewById(R.id.spnCinema);
        btnConfirmUpdateRoom = findViewById(R.id.btnConfirmUpdateRoom);

        cinemaQuery = new CinemaQuery(this);
        listCinemas = cinemaQuery.getCinemaNames();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.item_spinner, listCinemas);
        spnCinema.setAdapter(arrayAdapter);

        roomQuery = new RoomQuery(this);

        int id = getIntent().getIntExtra("id", 0);
        String name = getIntent().getStringExtra("name");
        int cinemaId = getIntent().getIntExtra("cinemaId", 0);
        editUpdateRoomName.setText(name);

        btnConfirmUpdateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = editUpdateRoomName.getText().toString().trim();
                int cinemaId = (int) spnCinema.getSelectedItemId() + 1;
                if (!newName.equals(""))
                {
                    Room room = new Room(id, newName, cinemaId);
                    Boolean result = roomQuery.updateRoom(room);
                    if (result == true)
                    {
                        Toast.makeText(UpdateRoomActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateRoomActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(UpdateRoomActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(UpdateRoomActivity.this, "Không cập nhật được do thiếu thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}