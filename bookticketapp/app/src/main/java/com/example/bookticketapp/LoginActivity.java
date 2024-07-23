package com.example.bookticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.btnLogIn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = email.getText().toString();
                String textPassword = password.getText().toString();
                if (textEmail.equals("user1@gmail.com") && textPassword.equals("123456")) {
                    Intent intent = new Intent(LoginActivity.this, LoggedInActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast toast = Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}