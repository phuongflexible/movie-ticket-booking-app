package com.example.bookticketapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.adapters.MoviePagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MovieDetailsActivity extends AppCompatActivity {
    private TabLayout tabLayoutMovie;
    private ViewPager2 viewPagerMovie;
    private MoviePagerAdapter moviePagerAdapter;
    private ImageButton ibtnVote, ibtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        findViewByIds();

        moviePagerAdapter = new MoviePagerAdapter(this);
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
    }
}