package com.example.bookticketapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.dao.CategoryQuery;
import com.example.bookticketapp.models.Category;
import com.example.bookticketapp.models.Movie;
import com.example.bookticketapp.utils.DatetimeUtils;

public class AddMovieActivity extends AppCompatActivity {

    EditText editAddMovieTitle, editAddMovieDescription, editAddMovieCategory, editAddMovieDuration, editAddMovieOpeningDay, editAddMovieRating;
    ImageView newImageFilm;
    Button btnConfirmAddMovie;
    CategoryQuery categoryQuery;
    private Uri imageFilePath;
    private Bitmap imageToStore;
    private static final int PICK_IMAGE_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        editAddMovieTitle = findViewById(R.id.editAddMovieTitle);
        editAddMovieDescription = findViewById(R.id.editAddMovieDescription);
        editAddMovieCategory = findViewById(R.id.editAddMovieCategory);
        editAddMovieDuration = findViewById(R.id.editAddMovieDuration);
        editAddMovieOpeningDay = findViewById(R.id.editAddMovieOpeningDay);
        editAddMovieRating = findViewById(R.id.editAddMovieRating);
        categoryQuery = new CategoryQuery(this);

        newImageFilm = findViewById(R.id.newImageFilm);
        btnConfirmAddMovie = findViewById(R.id.btnConfirmAddMovie);

        btnConfirmAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editAddMovieTitle.getText().toString().trim();
                String description = editAddMovieDescription.getText().toString().trim();
                String categoryName = editAddMovieCategory.getText().toString().trim();
                String duration = editAddMovieDuration.getText().toString().trim();
                String openingDay = editAddMovieOpeningDay.getText().toString().trim();
                String rating = editAddMovieRating.getText().toString().trim();

                if (title.equals("") || description.equals("") || categoryName.equals("") || duration.equals("") || openingDay.equals("") || rating.equals(""))
                {
                    Toast.makeText(AddMovieActivity.this, "Không thêm được do thiếu thông tin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Tim id the loai phim
                    int categoryId = categoryQuery.findCategoryId(categoryName);
                    //Chuyen opening day kieu string sang calendar
                    if (categoryId >= 1)
                    {
                        //Movie movie = new Movie(title, description, new Category(categoryId, categoryName), Integer.parseInt(duration), DatetimeUtils.stringToCalendar(openingDay, "dd/MM/yyyy"), Float.parseFloat(rating), );
                    }
                }
            }
        });

        newImageFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chooseImage(newImageFilm);
            }
        });
    }

    /*public void chooseImage(View objectView) {
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
        try
        {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && requestCode==RESULT_OK && data != null && data.getData() != null)
            {
                imageFilePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);

                newImageFilm.setImageBitmap(imageToStore);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }*/
}