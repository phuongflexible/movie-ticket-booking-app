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
import com.example.bookticketapp.models.Role;

public class AddRoleActivity extends AppCompatActivity {

    Button btnAddRole;
    EditText editRoleName;
    RoleQuery roleQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_role);

        editRoleName = findViewById(R.id.editRoleName);
        btnAddRole = findViewById(R.id.btnConfirmAddRole);
        roleQuery = new RoleQuery(this);

        btnAddRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roleName = editRoleName.getText().toString().trim();
                Boolean insert = roleQuery.addRole(new Role(roleName));
                if (insert == true) {
                    Toast.makeText(AddRoleActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddRoleActivity.this, AdminActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(AddRoleActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}