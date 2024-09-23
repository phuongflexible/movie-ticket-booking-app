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
import com.example.bookticketapp.models.Location;
import com.example.bookticketapp.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class AddCinemaActivity extends AppCompatActivity {

    EditText editAddCinemaName, editAddAddress;
    ImageView newImageCinema;
    Spinner spnLocateCinema;
    Button btnConfirmAddCinema;
    List<String> listCategoriesString = new ArrayList<>();
    LocationQuery locationQuery;
    ArrayAdapter arrayAdapter;
    CinemaQuery cinemaQuery;

    private Uri imageFilePath;
    private Bitmap imageToStore;
    private static final int PICK_IMAGE_REQUEST = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cinema);

        editAddAddress = findViewById(R.id.editAddAddress);
        editAddCinemaName = findViewById(R.id.editAddCinemaName);
        spnLocateCinema = findViewById(R.id.spnLocateCinema);
        newImageCinema = findViewById(R.id.newImageCinema);
        btnConfirmAddCinema = findViewById(R.id.btnConfirmAddCinema);

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

        btnConfirmAddCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editAddCinemaName.getText().toString().trim();
                String address = editAddAddress.getText().toString().trim();
                int locationId = (int) spnLocateCinema.getSelectedItemId() + 1;
                byte[] imageCinema = ImageUtils.bitmapToByteArray(imageToStore);

                if (name.equals("") || address.equals("") || locationId == -1 || imageCinema.equals(null))
                {
                    Toast.makeText(AddCinemaActivity.this, "Không thêm được do thiếu thông tin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Cinema cinema = new Cinema(name, address, imageCinema, locationId);
                    Boolean insert = cinemaQuery.addCinema(cinema);
                    if (insert == true)
                    {
                        Toast.makeText(AddCinemaActivity.this, "Thêm rạp phim thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddCinemaActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(AddCinemaActivity.this, "Thêm rạp phim thất bại", Toast.LENGTH_SHORT).show();
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