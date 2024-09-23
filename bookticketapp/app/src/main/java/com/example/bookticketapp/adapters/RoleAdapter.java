package com.example.bookticketapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.dao.RoleQuery;
import com.example.bookticketapp.events.RoleSelectListener;
import com.example.bookticketapp.models.Role;

import java.util.List;

public class RoleAdapter extends BaseAdapter {
    private List<Role> listRoles;
    private Context context;
    private LayoutInflater inflater;
    private RoleSelectListener listener;

    public RoleAdapter(List<Role> listRoles, Context context, RoleSelectListener listener) {
        this.listRoles = listRoles;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return listRoles.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = this.inflater.inflate(R.layout.item_role, null);
        TextView txtName = view.findViewById(R.id.listItemRole);
        txtName.setText(listRoles.get(i).getName());

        Button btnUpdateRole = view.findViewById(R.id.btnUpdateRole);
        btnUpdateRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updateRole(listRoles.get(i));
            }
        });

        Button btnDeleteRole = view.findViewById(R.id.btnDeleteRole);
        btnDeleteRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.deleteRole(listRoles.get(i));
            }
        });
        return view;
    }
}
