package com.example.bookticketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.dao.UserQuery;
import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.databinding.ActivitySignUpBinding;
import android.widget.Toast;
import com.example.bookticketapp.models.User;

public class SignUpActivity extends AppCompatActivity {

    TextView txtSignIn;
    private ActivitySignUpBinding binding;
    private UserQuery userQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userQuery = new UserQuery(this);

        binding.btnConfirmSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editSignUpFullName.getText().toString().trim();
                String gender = binding.editSignUpGender.getText().toString().trim();
                String email = binding.editSignUpEmail.getText().toString().trim();
                String phoneNumber = binding.editSignUpPhoneNumber.getText().toString().trim();
                String password = binding.editSignUpPassword.getText().toString().trim();
                String confirmPassword = binding.editSignUpConfirmPassword.getText().toString().trim();

                if (email.equals("") || name.equals("") || gender.equals("") || phoneNumber.equals("") || password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(SignUpActivity.this, "Thông tin bắt buộc", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(confirmPassword)) {
                        Boolean checkUserEmail = userQuery.checkEmail(email);
                        if (checkUserEmail == false) {
                            Boolean insert = userQuery.insertUser(new User(name, gender, email, phoneNumber, password, 2));
                            if (insert == true) {
                                Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUpActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(SignUpActivity.this, "Người dùng đã được đăng ký", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Mật khẩu sai", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}