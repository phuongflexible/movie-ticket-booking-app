package com.example.bookticketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.dao.UserQuery;
import com.example.bookticketapp.models.Role;
import com.example.bookticketapp.models.User;

public class AddUserActivity extends AppCompatActivity {
    EditText editAddUserFullName, editAddUserGender, editAddUserEmail, editAddUserPhoneNumber, editAddUserPassword;
    Button btnConfirmAddUser;
    UserQuery userQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        editAddUserFullName = findViewById(R.id.editAddUserFullName);
        editAddUserGender = findViewById(R.id.editAddUserGender);
        editAddUserEmail = findViewById(R.id.editAddUserEmail);
        editAddUserPhoneNumber = findViewById(R.id.editAddUserPhoneNumber);
        editAddUserPassword = findViewById(R.id.editAddUserPassword);
        btnConfirmAddUser = findViewById(R.id.btnConfirmAddUser);
        userQuery = new UserQuery(this);

        btnConfirmAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editAddUserFullName.getText().toString().trim();
                String gender = editAddUserGender.getText().toString().trim();
                String email = editAddUserEmail.getText().toString().trim();
                String phoneNumber = editAddUserPhoneNumber.getText().toString().trim();
                String password = editAddUserPassword.getText().toString().trim();

                if (name.equals("") || gender.equals("") || email.equals("") || phoneNumber.equals("") || password.equals(""))
                {
                    Toast.makeText(AddUserActivity.this, "Không thêm được do thiếu thông tin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    User user = new User(name, gender, email, phoneNumber, password, new Role(2, "User"));
                    //Kiem tra neu nguoi dung nay da co chua
                    Boolean checkEmail = userQuery.checkEmail(user.getEmail());
                    if (checkEmail == true)
                    {
                        Toast.makeText(AddUserActivity.this, "Đã có người dùng này", Toast.LENGTH_SHORT).show();;
                    }
                    else
                    {
                        Boolean insert = userQuery.insertUser(user);
                        if (insert == true)
                        {
                            Toast.makeText(AddUserActivity.this, "Thêm người dùng mới thành công", Toast.LENGTH_SHORT).show();;
                            Intent intent = new Intent(AddUserActivity.this, AdminActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(AddUserActivity.this, "Thêm người dùng mới thất bại", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
        });
    }
}