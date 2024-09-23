package com.example.bookticketapp.activities;

import androidx.annotation.NonNull;
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

import com.example.bookticketapp.fragments.AdminAccountFragment;
import com.example.bookticketapp.fragments.CategoryFragment;
import com.example.bookticketapp.fragments.CinemaAdminFragment;
import com.example.bookticketapp.fragments.LocationFragment;
import com.example.bookticketapp.fragments.MovieFragment;
import com.example.bookticketapp.fragments.PaymentMethodFragment;
import com.example.bookticketapp.fragments.RatingFragment;
import com.example.bookticketapp.fragments.RoleFragment;
import com.example.bookticketapp.fragments.RoomFragment;
import com.example.bookticketapp.fragments.SeatFragment;
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

        String email = getIntent().getStringExtra("email");
        String name = getIntent().getStringExtra("name");
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
                        replaceFragment(new MovieFragment());
                        break;
                    case R.id.nav_cinema:
                        replaceFragment(new CinemaAdminFragment());
                        break;
                    case R.id.nav_ticket:
                        replaceFragment(new TicketFragment());
                        break;
                    case R.id.nav_admin_account:
                        replaceFragment(new AdminAccountFragment());
                        break;
                    case R.id.nav_location:
                        replaceFragment(new LocationFragment());
                        break;
                    case R.id.nav_methodpayment:
                        replaceFragment(new PaymentMethodFragment());
                        break;
                    case R.id.nav_role:
                        replaceFragment(new RoleFragment());
                        break;
                    case R.id.nav_rating:
                        replaceFragment(new RatingFragment());
                        break;
                    case R.id.nav_room:
                        replaceFragment(new RoomFragment());
                        break;
                    case R.id.nav_seat:
                        replaceFragment(new SeatFragment());
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