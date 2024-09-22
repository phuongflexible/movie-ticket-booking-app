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

public class AddLocationActivity extends AppCompatActivity {

    EditText editLocationName;
    Button btnConfirmAddLocation;
    LocationQuery locationQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        editLocationName = findViewById(R.id.editLocationName);
        btnConfirmAddLocation = findViewById(R.id.btnConfirmAddLocation);
        locationQuery = new LocationQuery(this);

        btnConfirmAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editLocationName.getText().toString().trim();
                if (name.equals(""))
                {
                    Toast.makeText(AddLocationActivity.this, "Không thêm được do thiếu thông tin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Location location = new Location(name);
                    Boolean checkName = locationQuery.checkName(name);
                    if (checkName == true)
                    {
                        Boolean insert = locationQuery.addLocation(location);
                        if (insert == true)
                        {
                            Toast.makeText(AddLocationActivity.this, "Thêm địa điểm thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddLocationActivity.this, AdminActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(AddLocationActivity.this, "Thêm địa điểm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(AddLocationActivity.this, "Đã có địa điểm này", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}