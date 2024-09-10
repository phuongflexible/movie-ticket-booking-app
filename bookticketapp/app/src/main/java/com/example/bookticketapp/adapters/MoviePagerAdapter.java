package com.example.bookticketapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.bookticketapp.fragments.MovieInfoFragment;
import com.example.bookticketapp.fragments.MovieShowtimesFragment;
import com.example.bookticketapp.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviePagerAdapter extends FragmentStateAdapter {
    Movie movie;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> tabTitleList = new ArrayList<>();

    public MoviePagerAdapter(@NonNull FragmentActivity fragmentActivity, Movie movie) {
        super(fragmentActivity);
        this.movie = movie;
        addFragment(MovieShowtimesFragment.newInstance(movie), "Lịch chiếu");
        addFragment(MovieInfoFragment.newInstance(movie), "Thông tin");
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    public String getTabTitle(int position) {
        return tabTitleList.get(position);
    }

    private void addFragment(Fragment fragment, String tabTitle) {
        fragmentList.add(fragment);
        tabTitleList.add(tabTitle);
    }
}
