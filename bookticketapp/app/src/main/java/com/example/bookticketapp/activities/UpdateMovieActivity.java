package com.example.bookticketapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bookticketapp.R;
import com.example.bookticketapp.adapters.CategoryAdapter;
import com.example.bookticketapp.dao.CategoryQuery;
import com.example.bookticketapp.dao.MovieQuery;
import com.example.bookticketapp.models.Category;
import com.example.bookticketapp.models.Movie;
import com.example.bookticketapp.utils.DatetimeUtils;
import com.example.bookticketapp.utils.ImageUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UpdateMovieActivity extends AppCompatActivity {

    TextView editUpdateMovieTitle, editUpdateMovieDescription, editUpdateMovieDuration, editUpdateMovieOpeningDay, editUpdateMovieRating;
    Spinner spnUpdateCategory;
    ImageView updateNewImageFilm;
    Button btnConfirmUpdateMovie;

    ArrayAdapter cateAdapter;
    List<String> cateList;
    CategoryQuery categoryQuery;

    private Uri imageFilePath;
    private Bitmap imageToStore;
    private static final int PICK_IMAGE_REQUEST = 100;

    MovieQuery movieQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_movie);

        editUpdateMovieTitle = findViewById(R.id.editUpdateMovieTitle);
        editUpdateMovieDescription = findViewById(R.id.editUpdateMovieDescription);
        editUpdateMovieDuration = findViewById(R.id.editUpdateMovieDuration);
        editUpdateMovieOpeningDay = findViewById(R.id.editUpdateMovieOpeningDay);
        editUpdateMovieRating = findViewById(R.id.editUpdateMovieRating);
        spnUpdateCategory = findViewById(R.id.spnUpdateCategory);
        updateNewImageFilm = findViewById(R.id.updateNewImageFilm);
        btnConfirmUpdateMovie = findViewById(R.id.btnConfirmUpdateMovie);

        categoryQuery = new CategoryQuery(this);
        cateList = categoryQuery.getCateNames();
        cateAdapter = new ArrayAdapter(this, R.layout.item_spinner, cateList);
        spnUpdateCategory.setAdapter(cateAdapter);

        movieQuery = new MovieQuery(this);

        int id = getIntent().getIntExtra("id", 0);
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        int categoryId = getIntent().getIntExtra("categoryId", 0);
        int duration = getIntent().getIntExtra("duration", 0);
        String openingDay = getIntent().getStringExtra("openingDay");
        float rating = getIntent().getFloatExtra("rating", 0);

        editUpdateMovieTitle.setText(title);
        editUpdateMovieDescription.setText(description);
        editUpdateMovieDuration.setText(String.valueOf(duration));
        editUpdateMovieOpeningDay.setText(String.valueOf(openingDay));
        editUpdateMovieRating.setText(String.valueOf(rating));

        btnConfirmUpdateMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTitle = editUpdateMovieTitle.getText().toString().trim();
                String newDescription = editUpdateMovieDescription.getText().toString().trim();
                String newDurationString = editUpdateMovieDuration.getText().toString().trim();
                int newDuration = Integer.parseInt(newDurationString);
                String newOpeningDayString = editUpdateMovieOpeningDay.getText().toString().trim();
                Calendar newOpeningDay = DatetimeUtils.stringToCalendar(newOpeningDayString, DatetimeUtils.DATE_FORMAT);
                String newRatingString = editUpdateMovieRating.getText().toString().trim();
                float newRating = Float.parseFloat(newRatingString);
                int newCategoryId = (int) (spnUpdateCategory.getSelectedItemId() + 1);
                byte[] newImage = ImageUtils.bitmapToByteArray(imageToStore);

                if (newTitle.equals("") || newDescription.equals("") || newCategoryId <= 0 || newDuration <= 0 || newOpeningDay == null || newRating <=0 || newImage == null)
                {
                    Toast.makeText(UpdateMovieActivity.this, "Không chỉnh sửa được do thiếu thông tin", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Movie movie = new Movie(id, newTitle, newDescription, newCategoryId, newDuration, newOpeningDay, newRating, newImage);
                    Boolean update = movieQuery.updateMovie(movie);
                    if (update == true)
                    {
                        Toast.makeText(UpdateMovieActivity.this, "Chỉnh sửa thông tin thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateMovieActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(UpdateMovieActivity.this, "Chỉnh sửa thất bại", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        updateNewImageFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage(updateNewImageFilm);
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

                updateNewImageFilm.setImageBitmap(imageToStore);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}