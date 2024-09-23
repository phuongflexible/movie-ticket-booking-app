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
import com.example.bookticketapp.dao.MovieQuery;
import com.example.bookticketapp.dao.RatingQuery;
import com.example.bookticketapp.dao.UserQuery;
import com.example.bookticketapp.models.Rating;

import java.util.ArrayList;
import java.util.List;

public class UpdateRatingActivity extends AppCompatActivity {

    Spinner spnMovie, spnUser;
    EditText editUpdateRating;
    Button btnConfirmUpdateRating;
    List<String> listMovieName = new ArrayList<>();
    List<String>listUserName = new ArrayList<>();
    MovieQuery movieQuery;
    UserQuery userQuery;
    ArrayAdapter arrayUser, arrayMovie;
    RatingQuery ratingQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_rating);

        editUpdateRating = findViewById(R.id.editUpdateRating);
        spnMovie = findViewById(R.id.spnMovieRating);
        spnUser = findViewById(R.id.spnUser);
        btnConfirmUpdateRating = findViewById(R.id.btnConfirmUpdateRating);

        int id = getIntent().getIntExtra("id", 0);
        float rating = getIntent().getFloatExtra("rating", 0);

        editUpdateRating.setText(String.valueOf(rating));

        movieQuery = new MovieQuery(this);
        userQuery = new UserQuery(this);
        ratingQuery = new RatingQuery(this);
        listUserName = userQuery.getUserNames();
        arrayUser = new ArrayAdapter(this, R.layout.item_spinner, listUserName);
        spnUser.setAdapter(arrayUser);

        listMovieName = movieQuery.getMovieNames();
        arrayMovie = new ArrayAdapter(this, R.layout.item_spinner, listMovieName);
        spnMovie.setAdapter(arrayMovie);

        btnConfirmUpdateRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newRatingString = editUpdateRating.getText().toString();
                if (!newRatingString.equals(""))
                {
                    float newRating = Float.parseFloat(newRatingString);
                    int movieId = (int) spnMovie.getSelectedItemId() + 1;
                    int userId = (int) spnUser.getSelectedItemId() + 1;
                    Boolean update = ratingQuery.updateRating(new Rating(id, newRating, movieId, userId));
                    if (update == true)
                    {
                        Toast.makeText(UpdateRatingActivity.this, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateRatingActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(UpdateRatingActivity.this, "Chỉnh sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(UpdateRatingActivity.this, "Không chỉnh sửa được do thiếu thông tin", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }
}