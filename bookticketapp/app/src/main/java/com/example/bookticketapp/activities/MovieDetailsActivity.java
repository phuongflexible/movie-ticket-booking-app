package com.example.bookticketapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.adapters.MoviePagerAdapter;
import com.example.bookticketapp.dao.MovieQuery;
import com.example.bookticketapp.models.Movie;
import com.example.bookticketapp.utils.DatetimeUtils;
import com.example.bookticketapp.utils.ImageUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MovieDetailsActivity extends AppCompatActivity {
    private TabLayout tabLayoutMovie;
    private ViewPager2 viewPagerMovie;
    private MoviePagerAdapter moviePagerAdapter;
    private TextView txtTitle, txtDuration, txtOpeningDay, txtRating;
    private ImageView imgMovie;
    private ImageButton ibtnVote, ibtnBack;
    private MovieQuery movieQuery;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        findViewByIds();

        // Lấy ra id phim đã chọn trong danh sách phim và hiển thị
        movieQuery = new MovieQuery(this);

        Intent intent = getIntent();
        int movieId = intent.getIntExtra("movieId", -1);

        if (movieId != -1) {
            movie = movieQuery.getMovieById(movieId);
            if (movie != null) {
                //displayMovieDetails(movie);
            } else {
                Toast.makeText(this, "Không tìm thấy phim", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }

        moviePagerAdapter = new MoviePagerAdapter(this, movie);
        viewPagerMovie.setAdapter(moviePagerAdapter);

        new TabLayoutMediator(tabLayoutMovie, viewPagerMovie, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(moviePagerAdapter.getTabTitle(position));
            }
        }).attach();

        ibtnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MovieDetailsActivity.this);
                builder.setMessage("Đánh giá phim này");
                builder.show();
            }
        });

        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MovieDetailsActivity.this, "back", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViewByIds() {
        tabLayoutMovie = findViewById(R.id.tabLayoutMovie);
        viewPagerMovie = findViewById(R.id.viewPagerMovie);
        ibtnVote = findViewById(R.id.ibtnVote);
        ibtnBack = findViewById(R.id.ibtnBack);
        txtTitle = findViewById(R.id.txtMovieTitle);
        txtDuration = findViewById(R.id.txtMovieDuration);
        txtOpeningDay = findViewById(R.id.txtMovieOpeningDay);
        txtRating = findViewById(R.id.txtRating);
        imgMovie = findViewById(R.id.imgMovie);
    }

    /*private void displayMovieDetails(Movie movie) {
        // 'movie.getImage()' trả về byte[], cần chuyển sang bitmap để gán vào imgMovie
        Bitmap bitmap = ImageUtils.byteArrayToBitmap(movie.getImage());
        // 'movie.getOpeningDay()' trả về calendar, cần chuyển sang string để gán vào txtOpeningDay
        String openingDayString = DatetimeUtils.calendarToString(movie.getOpeningDay());

        imgMovie.setImageBitmap(bitmap);
        txtTitle.setText(movie.getTitle());
        txtDuration.setText(movie.getDuration() + " phút");
        txtOpeningDay.setText(openingDayString);
        txtRating.setText(movie.getRating() + "");
    }*/
}