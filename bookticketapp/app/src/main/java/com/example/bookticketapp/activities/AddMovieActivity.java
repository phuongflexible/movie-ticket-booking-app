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
import com.example.bookticketapp.dao.CategoryQuery;
import com.example.bookticketapp.dao.MovieQuery;
import com.example.bookticketapp.models.Category;
import com.example.bookticketapp.models.Movie;
import com.example.bookticketapp.utils.DatetimeUtils;
import com.example.bookticketapp.utils.ImageUtils;

import java.util.Calendar;
import java.util.List;

public class AddMovieActivity extends AppCompatActivity {

    EditText editAddMovieTitle, editAddMovieDescription, editAddMovieDuration, editAddMovieOpeningDay, editAddMovieRating;
    ImageView newImageFilm;
    Button btnConfirmAddMovie;
    Spinner spnCate;
    ArrayAdapter cateAdapter;
    List<String> cateList;
    CategoryQuery categoryQuery;
    MovieQuery movieQuery;
    private Uri imageFilePath;
    private Bitmap imageToStore;
    private static final int PICK_IMAGE_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        editAddMovieTitle = findViewById(R.id.editAddMovieTitle);
        editAddMovieDescription = findViewById(R.id.editAddMovieDescription);
        editAddMovieDuration = findViewById(R.id.editAddMovieDuration);
        editAddMovieOpeningDay = findViewById(R.id.editAddMovieOpeningDay);
        editAddMovieRating = findViewById(R.id.editAddMovieRating);
        newImageFilm = findViewById(R.id.newImageFilm);
        btnConfirmAddMovie = findViewById(R.id.btnConfirmAddMovie);
        spnCate = findViewById(R.id.spnCategory);

        movieQuery = new MovieQuery(this);
        categoryQuery = new CategoryQuery(this);

        // setup spinner thể loại phim
        cateList = categoryQuery.getCateNames();
        cateAdapter = new ArrayAdapter(this, R.layout.item_spinner, cateList);
        spnCate.setAdapter(cateAdapter);

        btnConfirmAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editAddMovieTitle.getText().toString().trim();
                String description = editAddMovieDescription.getText().toString().trim();
                String duration = editAddMovieDuration.getText().toString().trim();
                String openingDayString = editAddMovieOpeningDay.getText().toString().trim();
                String rating = editAddMovieRating.getText().toString().trim();

                if (title.equals("") || description.equals("") || duration.equals("") || openingDayString.equals("") || rating.equals(""))
                {
                    Toast.makeText(AddMovieActivity.this, "Không thêm được do thiếu thông tin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Tim id the loai phim
                    int categoryId = (int) (spnCate.getSelectedItemId() + 1);
                    //Chuyen opening day kieu string sang calendar
                    Calendar openingDay = DatetimeUtils.stringToCalendar(openingDayString, DatetimeUtils.DATE_FORMAT);
                    byte[] imageMovie = ImageUtils.bitmapToByteArray(imageToStore);

                    Movie movie = new Movie(title, description, categoryId, Integer.parseInt(duration),
                            openingDay, Float.parseFloat(rating), imageMovie);

                    if (movieQuery.addMovie(movie)) {
                        Toast.makeText(AddMovieActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddMovieActivity.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        newImageFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage(newImageFilm);
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

                newImageFilm.setImageBitmap(imageToStore);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}