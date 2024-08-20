package com.example.bookticketapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.bookticketapp.fragments.MovieInfoFragment;
import com.example.bookticketapp.fragments.MovieShowtimesFragment;

import java.util.ArrayList;
import java.util.List;

public class PagerMovieAdapter extends FragmentStateAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> tabTitleList = new ArrayList<>();

    public PagerMovieAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

        addFragment(new MovieShowtimesFragment(), "Lịch chiếu");
        addFragment(new MovieInfoFragment(), "Thông tin");
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
