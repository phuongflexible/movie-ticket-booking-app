package com.example.bookticketapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.bookticketapp.R;
import com.example.bookticketapp.adapters.PagerMovieAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MovieDetailsActivity extends AppCompatActivity {
    private TabLayout tabLayoutMovie;
    private ViewPager2 viewPagerMovie;
    private PagerMovieAdapter pagerMovieAdapter;
    private ImageButton ibtnVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        FindViewByIds();

        pagerMovieAdapter = new PagerMovieAdapter(this);
        viewPagerMovie.setAdapter(pagerMovieAdapter);

        new TabLayoutMediator(tabLayoutMovie, viewPagerMovie, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(pagerMovieAdapter.getTabTitle(position));
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
    }

    private void FindViewByIds() {
        tabLayoutMovie = findViewById(R.id.tabLayoutMovie);
        viewPagerMovie = findViewById(R.id.viewPagerMovie);
        ibtnVote = findViewById(R.id.ibtnVote);
    }
}