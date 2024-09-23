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
import com.example.bookticketapp.models.Room;
import com.example.bookticketapp.models.Seat;

import java.util.ArrayList;
import java.util.List;

public class UpdateSeatActivity extends AppCompatActivity {

    EditText editUpdateSeatName;
    Button btnConfirmUpdateSeat;
    Spinner spnRoom;
    CheckBox checkBoxSeat;
    SeatQuery seatQuery;
    RoomQuery roomQuery;
    List<String> listRooms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_seat);

        btnConfirmUpdateSeat = findViewById(R.id.btnConfirmUpdateSeat);
        editUpdateSeatName = findViewById(R.id.editUpdateSeatName);
        spnRoom = findViewById(R.id.spnRoom);
        checkBoxSeat = findViewById(R.id.checkBoxSeat);

        int id = getIntent().getIntExtra("id", 0);

        roomQuery = new RoomQuery(this);
        seatQuery = new SeatQuery(this);
        listRooms = roomQuery.getNameRoom();
        ArrayAdapter arrayRoom = new ArrayAdapter(this, R.layout.item_spinner, listRooms);
        spnRoom.setAdapter(arrayRoom);

        btnConfirmUpdateSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String seatNumber = editUpdateSeatName.getText().toString().trim();
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
                if (!seatNumber.equals(""))
                {
                    Seat seat = new Seat(id, seatNumber, roomId, isAvailable);
                    Boolean result = seatQuery.updateSeat(seat);
                    if (result == true)
                    {
                        Toast.makeText(UpdateSeatActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateSeatActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(UpdateSeatActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(UpdateSeatActivity.this, "Không cập nhật được do thiếu thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}