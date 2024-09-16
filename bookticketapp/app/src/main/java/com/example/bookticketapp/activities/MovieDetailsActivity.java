package com.example.bookticketapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.adapters.MoviePagerAdapter;
import com.example.bookticketapp.dao.MovieQuery;
import com.example.bookticketapp.dao.RatingQuery;
import com.example.bookticketapp.models.Movie;
import com.example.bookticketapp.utils.DatetimeUtils;
import com.example.bookticketapp.utils.ImageUtils;
import com.example.bookticketapp.utils.SessionManager;
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
    private RatingQuery ratingQuery;
    private Movie movie;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        findViewByIds();

        sessionManager = new SessionManager(this);
        movieQuery = new MovieQuery(this);
        ratingQuery = new RatingQuery(this);

        // Lấy ra id phim đã chọn trong danh sách phim và hiển thị
        Intent intent = getIntent();
        int movieId = intent.getIntExtra("movieId", -1);

        if (movieId != -1) {
            movie = movieQuery.getMovieById(movieId);
            if (movie != null) {
                displayMovieDetails(movie);
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
                if (sessionManager.isLoggedIn()) {
                    showDialogRating();
                } else {
                    showLoginDialog();
                }
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

    private void displayMovieDetails(Movie movie) {
        // 'movie.getImage()' trả về byte[], cần chuyển sang bitmap để gán vào imgMovie
        Bitmap bitmap = ImageUtils.byteArrayToBitmap(movie.getImage());
        // 'movie.getOpeningDay()' trả về calendar, cần chuyển sang string để gán vào txtOpeningDay
        String openingDayString = DatetimeUtils.calendarToString(movie.getOpeningDay());
        String formatString = String.format("%.1f", movie.getRating());

        imgMovie.setImageBitmap(bitmap);
        txtTitle.setText(movie.getTitle());
        txtDuration.setText(movie.getDuration() + " phút");
        txtOpeningDay.setText(openingDayString);
        txtRating.setText(formatString);
    }

    private void showDialogRating() {
        int userId = sessionManager.getUserId();

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rating);

        RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
        Button btnCancel = dialog.findViewById(R.id.btnCancel_rating);
        Button btnOK = dialog.findViewById(R.id.btnOK_rating);

        // Kiểm tra người dùng đã đánh giá phim này chưa, nếu có thì set cho ratingBar
        Float existingRating = ratingQuery.getRatingByUserAndMovie(userId, movie.getId());
        if (existingRating != null) {
            ratingBar.setRating(existingRating / 2);    // rating trong db là thang 10
        }

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float rating = ratingBar.getRating() * 2;    // rating trên thang 5 chuyển thành thang 10
                String formatString = String.format("%.0f", movie.getRating());

                if (ratingQuery.addOrUpdateRating(rating, movie.getId(), userId)) {
                    Toast.makeText(MovieDetailsActivity.this,
                            "Bạn đã đánh giá phim này " + formatString + " điểm",
                            Toast.LENGTH_SHORT).show();

                    movieQuery.updateMovieRating(movie.getId());
                    reloadActivity();
                } else {
                    Toast.makeText(MovieDetailsActivity.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showLoginDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_login);

        TextView txtLogin = dialog.findViewById(R.id.txtLogin_booking);
        Button btnCancel = dialog.findViewById(R.id.btn_Cancel_login);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetailsActivity.this, LoginActivity.class);
                startActivity(intent);

                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void reloadActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}