package com.example.bookticketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.databinding.ActivityLoginBinding;
import android.widget.Toast;

import com.example.bookticketapp.R;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);

        binding.btnConfirmSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.editSignInEmail.getText().toString();
                String password = binding.editSignInPassword.getText().toString();
                //RadioGroup radioGroup = binding.userRoleGroup;
                //Integer selectedId = radioGroup.getCheckedRadioButtonId();

                if (email.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Thông tin bắt buộc", Toast.LENGTH_SHORT).show();
                } else {
                    //RadioButton radioButton = radioGroup.findViewById(selectedId);
                    Boolean checkAccount = databaseHelper.checkEmailPassword(email, password);
                    if (checkAccount == true) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}