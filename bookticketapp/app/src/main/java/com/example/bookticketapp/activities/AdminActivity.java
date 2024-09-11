package com.example.bookticketapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import com.example.bookticketapp.R;
import com.example.bookticketapp.databinding.ActivityAdminBinding;
import com.example.bookticketapp.fragments.AccountFragment;
import com.example.bookticketapp.fragments.AdminFragment;
import com.example.bookticketapp.fragments.CategoryFragment;
import com.example.bookticketapp.fragments.CinemaFragment;
import com.example.bookticketapp.fragments.FilmFragment;
import com.example.bookticketapp.fragments.HomeFragment;
import com.example.bookticketapp.fragments.TicketFragment;
import com.example.bookticketapp.fragments.UserFragment;

public class AdminActivity extends AppCompatActivity {
    ActivityAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new CategoryFragment());

        binding.bottomNavBarAdmin.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.category:
                    replaceFragment(new CategoryFragment());
                    break;
                case R.id.film:
                    replaceFragment(new FilmFragment());
                    break;
                case R.id.ticket:
                    replaceFragment(new TicketFragment());
                    break;
                case R.id.manageuser:
                    replaceFragment(new UserFragment());
                    break;
                case R.id.user:
                    replaceFragment(new AdminFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}