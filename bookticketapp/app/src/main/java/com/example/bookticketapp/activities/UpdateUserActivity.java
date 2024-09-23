package com.example.bookticketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.dao.RoleQuery;
import com.example.bookticketapp.dao.UserQuery;
import com.example.bookticketapp.fragments.CategoryFragment;
import com.example.bookticketapp.fragments.UserFragment;
import com.example.bookticketapp.models.Role;
import com.example.bookticketapp.models.User;

public class UpdateUserActivity extends AppCompatActivity {

    EditText editUpdateUserName, editUpdateUserGender, editUpdateUserPhoneNumber, editUpdateUserEmail, editUpdateUserRole;
    Button btnConfirmUpdateUser;
    UserQuery userQuery;
    RoleQuery roleQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        editUpdateUserName = findViewById(R.id.editUpdateUserName);
        editUpdateUserGender = findViewById(R.id.editUpdateUserGender);
        editUpdateUserPhoneNumber = findViewById(R.id.editUpdateUserPhoneNumber);
        editUpdateUserEmail = findViewById(R.id.editUpdateUserEmail);
        editUpdateUserRole = findViewById(R.id.editUpdateUserRole);

        userQuery = new UserQuery(this);
        roleQuery = new RoleQuery(this);

        Intent intent = getIntent();
        int id = intent.getIntExtra("userId", 0);
        String name = intent.getStringExtra("userName");
        String gender = intent.getStringExtra("userGender");
        String email = intent.getStringExtra("userEmail");
        String phoneNumber = intent.getStringExtra("userPhoneNumber");
        String roleName = intent.getStringExtra("userRole");

        editUpdateUserName.setText(name);
        editUpdateUserGender.setText(gender);
        editUpdateUserEmail.setText(email);
        editUpdateUserPhoneNumber.setText(phoneNumber);
        editUpdateUserRole.setText(roleName);

        btnConfirmUpdateUser = findViewById(R.id.btnConfirmUpdateUser);

        btnConfirmUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = editUpdateUserName.getText().toString();
                String newGender = editUpdateUserGender.getText().toString();
                String newEmail = editUpdateUserEmail.getText().toString();
                String newPhoneNumber = editUpdateUserPhoneNumber.getText().toString();
                String newRole = editUpdateUserRole.getText().toString();

                if (newName.equals("") || newGender.equals("") || newEmail.equals("") || newPhoneNumber.equals("") || newRole.equals("")) {
                    Toast.makeText(UpdateUserActivity.this, "Không chỉnh sửa được do thiếu thông tin", Toast.LENGTH_LONG).show();
                }
                else {
                    //Tim id vai tro
                    int roleId = roleQuery.findRoleId(newRole);
                    Boolean update = userQuery.updateUser(new User(id, new Role(roleId, newRole), name, gender, phoneNumber, email), newName, newGender, newEmail, newPhoneNumber, newRole);
                    if (update == true) {
                        Toast.makeText(UpdateUserActivity.this, "Chỉnh sửa thông tin thành công", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(UpdateUserActivity.this, AdminActivity.class);
                        startActivity(intent1);
                    }
                    else {
                        Toast.makeText(UpdateUserActivity.this, "Không chỉnh sửa được do thiếu thông tin", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


}