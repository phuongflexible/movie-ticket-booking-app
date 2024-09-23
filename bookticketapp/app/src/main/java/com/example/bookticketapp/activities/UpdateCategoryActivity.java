package com.example.bookticketapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.bookticketapp.R;
import com.example.bookticketapp.dao.CategoryQuery;
import com.example.bookticketapp.models.Category;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateCategoryActivity extends AppCompatActivity {
    EditText editUpdateCategoryName;
    Button btnUpdateCategory;
    CategoryQuery cateQuery;
    Category category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);
        editUpdateCategoryName = findViewById(R.id.editUpdateCategoryName);
        btnUpdateCategory = findViewById(R.id.btnConfirmUpdateCategory);
        cateQuery = new CategoryQuery(this);

        Intent intent = getIntent();
        Integer id = intent.getIntExtra("categoryId", 0);
        String name = intent.getStringExtra("categoryName");
        btnUpdateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newCategoryName = editUpdateCategoryName.getText().toString();
                if (newCategoryName.equals("")) {
                    Toast.makeText(UpdateCategoryActivity.this, "Điền tên mới danh mục", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean update = cateQuery.updateCategory(new Category(id, name), newCategoryName);
                    if (update == true) {
                        Toast.makeText(UpdateCategoryActivity.this, "Chỉnh sửa danh mục mới thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateCategoryActivity.this, AdminActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(UpdateCategoryActivity.this, "Chỉnh sửa danh mục thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}