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
import com.example.bookticketapp.events.UserSelectListener;
import com.example.bookticketapp.models.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private ArrayList<User> listUsers = new ArrayList<>();
    private Context context;
    private UserSelectListener listener;

    public UserAdapter(ArrayList<User> listUsers, Context context, UserSelectListener listener) {
        this.listUsers = listUsers;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = listUsers.get(position);
        holder.userName.setText(user.getName());
        holder.userGender.setText(user.getGender());
        holder.userPhoneNumber.setText(user.getPhoneNumber());
        holder.userEmail.setText(user.getEmail());
        holder.userRole.setText(user.getRole().getName());
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUpdateUserButtonClicked(listUsers.get(holder.getAdapterPosition()));
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteUserButtonClicked(listUsers.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userName;
        private TextView userGender;
        private TextView userPhoneNumber;
        private TextView userEmail;
        private TextView userRole;
        private Button btnUpdate;
        private Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.txtNameUser);
            userGender = itemView.findViewById(R.id.txtGenderUser);
            userPhoneNumber = itemView.findViewById(R.id.txtPhoneNumberUser);
            userEmail = itemView.findViewById(R.id.txtEmailUser);
            userRole = itemView.findViewById(R.id.txtRoleUser);
            btnUpdate = itemView.findViewById(R.id.btnUpdateUser);
            btnDelete = itemView.findViewById(R.id.btnDeleteUser);
        }
    }
}
