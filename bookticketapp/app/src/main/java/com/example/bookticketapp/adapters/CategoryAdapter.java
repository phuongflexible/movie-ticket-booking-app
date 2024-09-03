package com.example.bookticketapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.events.SelectListener;
import com.example.bookticketapp.models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ArrayList<Category> listCategories = new ArrayList<>();
    private Context context;
    private SelectListener listener;

    public CategoryAdapter(ArrayList<Category> list, Context cont, SelectListener listener) {
        this.listCategories = list;
        this.context = cont;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Category cate = listCategories.get(position);
        holder.categoryName.setText(cate.getName());
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUpdateButtonClicked(listCategories.get(holder.getAdapterPosition()));
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteButtonClicked(listCategories.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryName;
        private Button btnUpdate;
        private Button btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.txtCategoryName);
            btnUpdate = itemView.findViewById(R.id.btnUpdateCategory);
            btnDelete = itemView.findViewById(R.id.btnDeleteCategory);
        }
    }
}
