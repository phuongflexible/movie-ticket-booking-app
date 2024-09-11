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

public class AddCategoryActivity extends AppCompatActivity {
    EditText editCategoryName;
    Button btnAddCategory;
    CategoryQuery cateQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        editCategoryName = findViewById(R.id.editCategoryName);
        btnAddCategory = findViewById(R.id.btnConfirmAddCategory);
        cateQuery = new CategoryQuery(this);

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoryName = editCategoryName.getText().toString().trim();
                if (categoryName.equals("")) {
                    Toast.makeText(AddCategoryActivity.this, "Điền tên thể loại phim", Toast.LENGTH_SHORT).show();
                } else {
                    Category category = new Category(categoryName);
                    Boolean checkName = cateQuery.checkCategory(category);
                    if (checkName == true) {
                        Toast.makeText(AddCategoryActivity.this, "Đã có thể loại phim này", Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean insert =  cateQuery.addCategory(category);
                        if (insert == true) {
                            Toast.makeText(AddCategoryActivity.this, "Thêm danh mục mới thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddCategoryActivity.this, AdminActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AddCategoryActivity.this, "Thêm danh mục mới thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}