package com.example.bookticketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.dao.UserQuery;
import com.example.bookticketapp.databinding.ActivityProfileBinding;
import com.example.bookticketapp.models.User;
import com.example.bookticketapp.utils.SessionManager;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    SessionManager sessionManager;
    UserQuery userQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);
        userQuery = new UserQuery(this);

        int userId = sessionManager.getUserId();
        User user = userQuery.getUserById(userId);

        binding.accountFullname.setText(user.getName());
        binding.accountGender.setText(user.getGender());
        binding.accountEmail.setText(user.getEmail());
        binding.accountPhoneNumber.setText(user.getPhoneNumber());
        binding.accountRole.setText(user.getRole().getName());

        binding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
                Toast.makeText(ProfileActivity.this, sessionManager.getUserId() + "", Toast.LENGTH_SHORT).show();
                Log.d("logout - userId:", sessionManager.getUserId() + "");
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}