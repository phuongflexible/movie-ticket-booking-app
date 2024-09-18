package com.example.bookticketapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.activities.AddCategoryActivity;
import com.example.bookticketapp.activities.AdminActivity;
import com.example.bookticketapp.activities.UpdateCategoryActivity;
import com.example.bookticketapp.adapters.CategoryAdapter;
import com.example.bookticketapp.dao.CategoryQuery;
import com.example.bookticketapp.events.SelectListener;
import com.example.bookticketapp.models.Category;

import java.util.ArrayList;

public class CategoryFragment extends Fragment implements SelectListener {
    Activity context;
    private ArrayList<Category> categoryArrayList;
    private CategoryQuery cateQuery;
    private CategoryAdapter cateAdapter;
    private RecyclerView cateRV;
    Button btnAddCategory;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        categoryArrayList = new ArrayList<>();
        cateQuery = new CategoryQuery(context);

        categoryArrayList = cateQuery.listCategories();

        cateAdapter = new CategoryAdapter(categoryArrayList, context, this);
        cateRV = view.findViewById(R.id.viewCategories);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        cateRV.setLayoutManager(linearLayoutManager);
        cateRV.setAdapter(cateAdapter);
        btnAddCategory = view.findViewById(R.id.btnAddCategory);

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddCategoryActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onUpdateButtonClicked(Category cate) {
        Integer id = cate.getId();
        String name = cate.getName();
        Intent intent = new Intent(context, UpdateCategoryActivity.class);
        intent.putExtra("cagoryId", id);
        intent.putExtra("categoryName", name);
        startActivity(intent);
    }

    @Override
    public void onDeleteButtonClicked(Category cate) {
        Boolean result = cateQuery.deleteCategory(cate.getName());
        if (result == true) {
            Toast.makeText(context, "Xoá thể loại thành công", Toast.LENGTH_SHORT).show();
            reloadCategoryFragment();
        }
        else {
            Toast.makeText(context, "Xoá thể loại thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void reloadCategoryFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new CategoryFragment());
        fragmentTransaction.commit();
    }
}