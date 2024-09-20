package com.example.bookticketapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bookticketapp.R;
import com.example.bookticketapp.dao.UserQuery;
import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.databinding.ActivityMainBinding;
import com.example.bookticketapp.fragments.AccountFragment;
import com.example.bookticketapp.fragments.CinemaFragment;
import com.example.bookticketapp.fragments.HistoryFragment;
import com.example.bookticketapp.fragments.HomeFragment;
import com.example.bookticketapp.utils.SessionManager;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    SessionManager sessionManager;
    UserQuery userQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Copy database từ assets vào máy ảo
        DatabaseHelper.copyDatabase(this);

        sessionManager = new SessionManager(this);
        userQuery = new UserQuery(this);

        if (sessionManager.isLoggedIn()) {    // nếu đã đăng nhập
            int userId = sessionManager.getUserId();

            if (userQuery.getUserRoleById(userId) == 1) {    // nếu role là admin
                Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        }

        // ở BookingActivity, khi đặt vé thành công thì chuyển tới HistoryFragment
        Intent intent = getIntent();
        if (intent != null && intent.getBooleanExtra("showHistoryFragment", false)) {
            replaceFragment(new HistoryFragment());
        } else {
            replaceFragment(new HomeFragment());
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.cinema:
                    replaceFragment(new CinemaFragment());
                    break;
                case R.id.history:
                    replaceFragment(new HistoryFragment());
                    break;
                case R.id.account:
                    replaceFragment(new AccountFragment());
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