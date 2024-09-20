package com.example.bookticketapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.example.bookticketapp.R;
import com.example.bookticketapp.fragments.AccountFragment;
import com.example.bookticketapp.fragments.AdminFragment;
import com.example.bookticketapp.fragments.CategoryFragment;
import com.example.bookticketapp.fragments.CinemaAdminFragment;
import com.example.bookticketapp.fragments.CinemaFragment;
import com.example.bookticketapp.fragments.FilmFragment;
import com.example.bookticketapp.fragments.HomeFragment;
import com.example.bookticketapp.fragments.TicketFragment;
import com.example.bookticketapp.fragments.UserFragment;
import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        replaceFragment(new CategoryFragment());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navBarAdmin);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.nav_user:
                        replaceFragment(new UserFragment());
                        break;
                    case R.id.nav_category:
                        replaceFragment(new CategoryFragment());
                        break;
                    case R.id.nav_movie:
                        replaceFragment(new FilmFragment());
                        break;
                    case R.id.nav_cinema:
                        replaceFragment(new CinemaAdminFragment());
                        break;
                    case R.id.nav_ticket:
                        replaceFragment(new TicketFragment());
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

}