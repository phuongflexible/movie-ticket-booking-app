package com.example.bookticketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.bookticketapp.dao.UserQuery;
import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.databinding.ActivityLoginBinding;
import android.widget.Toast;

import com.example.bookticketapp.fragments.HomeFragment;
import com.example.bookticketapp.models.User;
import com.example.bookticketapp.utils.PasswordUtils;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    UserQuery userQuery;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userQuery = new UserQuery(this);
        user = new User();

        binding.btnConfirmSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.editSignInEmail.getText().toString().trim();
                String password = binding.editSignInPassword.getText().toString().trim();

                //Kiem tra dien day du thong tin chua
                if (email.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Thông tin bắt buộc", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean checkAccount = userQuery.checkEmailPassword(email, password);
                    //Kiem tra  co dung email va password ko
                    if (checkAccount == true)
                    {
                        //Lay thong tin cua nguoi nay
                        user = getPersonalInfo(email);
                        //Neu tim thay thong tin
                        if (user != null)
                        {
                            //Kiem tra co dung vai tro ko
                            if (user.getRole().getId() == 1)
                            {  //Neu la admin
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                if (user.getRole().getId() == 2)
                                { //Neu la user
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else
                                {  //Neu sai vai tro
                                    Toast.makeText(LoginActivity.this, "Không đăng nhập được do sai vai trò", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại - " + PasswordUtils.encodePassword(password), Toast.LENGTH_SHORT).show();
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
    public User getPersonalInfo(String email) {
        User user = userQuery.getUser(email);
        if (user == null) {
            return null;
        } else {
            return user;
        }
    }
}