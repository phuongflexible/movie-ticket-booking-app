package com.example.bookticketapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.activities.AddRoleActivity;
import com.example.bookticketapp.activities.UpdateLocationActivity;
import com.example.bookticketapp.activities.UpdateRoleActivity;
import com.example.bookticketapp.adapters.RoleAdapter;
import com.example.bookticketapp.dao.RoleQuery;
import com.example.bookticketapp.events.RoleSelectListener;
import com.example.bookticketapp.models.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleFragment extends Fragment implements RoleSelectListener {
    Activity context;
    List<Role> listRoles;
    ListView lvRole;
    Button btnAddRole;
    RoleQuery roleQuery;
    RoleAdapter roleAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_role, container, false);
        lvRole = view.findViewById(R.id.lvRole);
        btnAddRole = view.findViewById(R.id.btnAddRole);

        roleQuery = new RoleQuery(context);
        listRoles = new ArrayList<>();
        listRoles = roleQuery.getAllRoles();
        roleAdapter = new RoleAdapter(listRoles, context, this);
        lvRole.setAdapter(roleAdapter);

        btnAddRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddRoleActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void updateRole(Role role) {
        int id = role.getId();
        String name = role.getName();
        Intent intent = new Intent(context, UpdateRoleActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    @Override
    public void deleteRole(Role role) {
        Boolean delete = roleQuery.deleteRole(role.getId());
        if (delete == true)
        {
            Toast.makeText(context, "Xoá vai trò thành công", Toast.LENGTH_SHORT).show();
            reloadRoleFragment();
        }
        else
        {
            Toast.makeText(context, "Xoá vai trò thất bại", Toast.LENGTH_SHORT).show();
        }
    }
    private void reloadRoleFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new RoleFragment());
        fragmentTransaction.commit();
    }
}