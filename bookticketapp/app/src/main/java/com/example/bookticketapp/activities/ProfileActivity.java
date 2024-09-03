package com.example.bookticketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String fullName = intent.getStringExtra("fullName");
        String gender = intent.getStringExtra("gender");
        String email = intent.getStringExtra("email");
        String phoneNumber = intent.getStringExtra("phoneNumber");
        Integer roleId = intent.getIntExtra("role", 1);

        binding.accountFullname.setText(fullName);
        binding.accountGender.setText(gender);
        binding.accountEmail.setText(email);
        binding.accountPhoneNumber.setText(phoneNumber);
        if (roleId == 1) {
            binding.accountRole.setText("Admin");
        } else if (roleId == 2) {
            binding.accountRole.setText("Người dùng");
        }

        binding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}