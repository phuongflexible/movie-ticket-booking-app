package com.example.bookticketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.dao.RoomQuery;
import com.example.bookticketapp.dao.SeatQuery;
import com.example.bookticketapp.models.Seat;

import java.util.ArrayList;
import java.util.List;

public class AddSeatActivity extends AppCompatActivity {

    EditText editAddSeatName;
    Spinner spnRoom;
    CheckBox checkBoxSeat;
    Button btnConfirmAddSeat;

    List<String> listRooms = new ArrayList<>();
    SeatQuery seatQuery;
    RoomQuery roomQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_seat);

        editAddSeatName = findViewById(R.id.editAddSeatName);
        spnRoom = findViewById(R.id.spnRoom);
        checkBoxSeat = findViewById(R.id.checkBoxSeat);
        btnConfirmAddSeat = findViewById(R.id.btnConfirmAddSeat);

        roomQuery = new RoomQuery(this);
        seatQuery = new SeatQuery(this);
        listRooms = roomQuery.getNameRoom();
        ArrayAdapter arrayRoom = new ArrayAdapter(this, R.layout.item_spinner, listRooms);
        spnRoom.setAdapter(arrayRoom);

        btnConfirmAddSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = editAddSeatName.getText().toString().trim();
                int roomId = (int) spnRoom.getSelectedItemId() + 1;
                boolean isAvailable;
                if (checkBoxSeat.isChecked())
                {
                    isAvailable = true;
                }
                else
                {
                    isAvailable = false;
                }
                if (!newName.equals(""))
                {
                    Seat seat = new Seat(newName, roomId, isAvailable);
                    Boolean insert = seatQuery.addSeat(seat);
                    if (insert == true)
                    {
                        Toast.makeText(AddSeatActivity.this, "Thêm ghế ngồi thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddSeatActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(AddSeatActivity.this, "Thêm ghế ngồi thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}