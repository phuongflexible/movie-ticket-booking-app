package com.example.bookticketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.dao.LocationQuery;
import com.example.bookticketapp.dao.RoleQuery;
import com.example.bookticketapp.models.Location;
import com.example.bookticketapp.models.Role;

public class UpdateRoleActivity extends AppCompatActivity {
    EditText editUpdateRoleName;
    Button btnConfirmUpdateRole;
    RoleQuery roleQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_role);

        editUpdateRoleName = findViewById(R.id.editUpdateRoleName);
        btnConfirmUpdateRole = findViewById(R.id.btnConfirmUpdateRole);

        int id = getIntent().getIntExtra("id", 0);
        String name = getIntent().getStringExtra("name");
        editUpdateRoleName.setText(name);

        roleQuery = new RoleQuery(this);

        btnConfirmUpdateRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = editUpdateRoleName.getText().toString().trim();
                Boolean checkName = roleQuery.checkName(newName);
                if (checkName == true)
                {
                    Role role = new Role(id, newName);
                    Boolean update = roleQuery.updateRole(role);
                    if (update == true)
                    {
                        Toast.makeText(UpdateRoleActivity.this, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateRoleActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(UpdateRoleActivity.this, "Chỉnh sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(UpdateRoleActivity.this, "Đã có địa điểm này", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}