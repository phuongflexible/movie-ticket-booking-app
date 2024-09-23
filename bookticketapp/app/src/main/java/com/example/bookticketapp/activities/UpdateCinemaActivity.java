package com.example.bookticketapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.dao.CinemaQuery;
import com.example.bookticketapp.dao.LocationQuery;
import com.example.bookticketapp.models.Cinema;
import com.example.bookticketapp.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class UpdateCinemaActivity extends AppCompatActivity {

    EditText editUpdateCinemaName, editUpdateAddress;
    ImageView newImageCinema;
    Spinner spnLocateCinema;
    Button btnConfirmUpdateCinema;

    LocationQuery locationQuery;
    ArrayAdapter arrayAdapter;
    CinemaQuery cinemaQuery;
    List<String> listCategoriesString = new ArrayList<>();

    private Uri imageFilePath;
    private Bitmap imageToStore;
    private static final int PICK_IMAGE_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cinema);

        editUpdateCinemaName = findViewById(R.id.editUpdateCinemaName);
        editUpdateAddress = findViewById(R.id.editUpdateAddress);
        newImageCinema = findViewById(R.id.newImageCinema);
        spnLocateCinema = findViewById(R.id.spnLocateCinema);
        btnConfirmUpdateCinema = findViewById(R.id.btnConfirmUpdateCinema);

        int id = getIntent().getIntExtra("id", 0);
        String name = getIntent().getStringExtra("name");
        String address = getIntent().getStringExtra("address");

        editUpdateCinemaName.setText(name);
        editUpdateAddress.setText(address);

        locationQuery = new LocationQuery(this);
        listCategoriesString = locationQuery.getLocationNames();
        arrayAdapter = new ArrayAdapter(this, R.layout.item_spinner, listCategoriesString);
        spnLocateCinema.setAdapter(arrayAdapter);
        cinemaQuery = new CinemaQuery(this);

        newImageCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage(newImageCinema);
            }
        });

        btnConfirmUpdateCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = editUpdateCinemaName.getText().toString().trim();
                String newAddress = editUpdateAddress.getText().toString().trim();
                int newLocationId = (int) spnLocateCinema.getSelectedItemId() + 1;
                byte[] newImageCinema = ImageUtils.bitmapToByteArray(imageToStore);

                if (newName.equals("") || newAddress.equals("") || newLocationId == -1 || newImageCinema.equals(null))
                {
                    Toast.makeText(UpdateCinemaActivity.this, "Không chỉnh sửa được do thiếu thông tin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Cinema cinema = new Cinema(id, newName, newAddress, newImageCinema, newLocationId);
                    Boolean update = cinemaQuery.updateCinema(cinema);

                    if (update == true)
                    {
                        Toast.makeText(UpdateCinemaActivity.this, "Chỉnh sửa rạp phim thành công", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(UpdateCinemaActivity.this, AdminActivity.class);
                        startActivity(intent1);
                    }
                    else
                    {
                        Toast.makeText(UpdateCinemaActivity.this, "Chỉnh sửa rạp phim thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

    }

    public void chooseImage(View objectView) {
        try
        {
            Intent objectIntent = new Intent();
            objectIntent.setType("image/*");

            objectIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(objectIntent, PICK_IMAGE_REQUEST);
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageFilePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);

                newImageCinema.setImageBitmap(imageToStore);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}