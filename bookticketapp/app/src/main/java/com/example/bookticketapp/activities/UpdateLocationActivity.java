package com.example.bookticketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.dao.LocationQuery;
import com.example.bookticketapp.models.Location;

public class UpdateLocationActivity extends AppCompatActivity {
    EditText editUpdateLocationName;
    Button btnConfirmUpdateLocation;
    LocationQuery locationQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_location);

        editUpdateLocationName = findViewById(R.id.editUpdateLocationName);
        btnConfirmUpdateLocation = findViewById(R.id.btnConfirmUpdateLocation);

        int id = getIntent().getIntExtra("id", 0);
        String name = getIntent().getStringExtra("name");
        editUpdateLocationName.setText(name);

        locationQuery = new LocationQuery(this);

        btnConfirmUpdateLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = editUpdateLocationName.getText().toString().trim();
                Boolean checkName = locationQuery.checkName(newName);
                if (checkName == true)
                {
                    Location location = new Location(id, newName);
                    Boolean update = locationQuery.updateLocation(location);
                    if (update == true)
                    {
                        Toast.makeText(UpdateLocationActivity.this, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateLocationActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(UpdateLocationActivity.this, "Chỉnh sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(UpdateLocationActivity.this, "Đã có địa điểm này", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}