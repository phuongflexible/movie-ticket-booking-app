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

import com.example.bookticketapp.models.User;

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
                String email = binding.editSignInEmail.getText().toString();
                String password = binding.editSignInPassword.getText().toString();
                RadioGroup radioGroup = binding.userRoleGroup;
                Integer selectedId = radioGroup.getCheckedRadioButtonId();

                //Kiem tra dien day du thong tin chua
                if (email.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Thông tin bắt buộc", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton radioButton = radioGroup.findViewById(selectedId);
                    Boolean checkAccount = userQuery.checkEmailPassword(email, password);
                    //Kiem tra  co dung email va password ko
                    if (checkAccount == true) {
                        //Kiem tra da danh dau vai tro chua
                        if (radioGroup.getCheckedRadioButtonId() == -1) {
                            Toast.makeText(LoginActivity.this, "Chọn vai trò", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String role = radioButton.getText().toString();
                            //Kiem tra co dung vai tro ko
                            user = getPersonalInfo(email);  //Lay thong tin cua nguoi nay
                            if (role.equals("Quản lý")) {  //Neu nguoi nay danh vao o quan ly
                                if (user.getRoleId() == 1) {  //Thong tin ve vai tro trong db la quan ly
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Không đăng nhập được do sai vai trò", Toast.LENGTH_SHORT).show();
                                }
                            } else {    //Neu nguoi nay danh vao o người dùng
                                if (user.getRoleId() == 2) { //Thong tin ve vai tro trong db la nguoi dung
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Không đăng nhập được do sai vai trò", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
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
    public User getPersonalInfo(String email) {
        User user = userQuery.getUser(email);
        if (user == null) {
            return null;
        } else {
            return user;
        }
    }
}