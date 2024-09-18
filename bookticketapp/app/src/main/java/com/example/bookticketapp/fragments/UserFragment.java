package com.example.bookticketapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.bookticketapp.activities.AddUserActivity;
import com.example.bookticketapp.activities.AdminActivity;
import com.example.bookticketapp.activities.UpdateUserActivity;
import com.example.bookticketapp.adapters.UserAdapter;
import com.example.bookticketapp.dao.UserQuery;
import com.example.bookticketapp.events.UserSelectListener;
import com.example.bookticketapp.models.Category;
import com.example.bookticketapp.models.Role;
import com.example.bookticketapp.models.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment implements UserSelectListener{
    Activity context;
    Button btnAddUser;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserQuery userQuery = new UserQuery(context);
        ArrayList<User> userArrayList = userQuery.readUsers();
        UserAdapter userAdapter = new UserAdapter(userArrayList, context, this);
        RecyclerView userRV = view.findViewById(R.id.viewUsers);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        userRV.setLayoutManager(linearLayoutManager);
        userRV.setAdapter(userAdapter);

        btnAddUser = view.findViewById(R.id.btnAddUser);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddUserActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onUpdateUserButtonClicked(User user) {
        int id = user.getId();
        String name = user.getName();
        String email = user.getEmail();
        String gender = user.getGender();
        String phoneNumber = user.getPhoneNumber();
        Role role = user.getRole();
        Intent intent = new Intent(context, UpdateUserActivity.class);
        intent.putExtra("userId", id);
        intent.putExtra("userName", name);
        intent.putExtra("userEmail", email);
        intent.putExtra("userGender", gender);
        intent.putExtra("userPhoneNumber", phoneNumber);
        intent.putExtra("userRole", role.getName());
        startActivity(intent);
    }

    @Override
    public void onDeleteUserButtonClicked(User user) {
        UserQuery userQuery = new UserQuery(context);
        Boolean result = userQuery.deleteUser(user.getName());
        if (result == true) {
            Toast.makeText(context, "Xoá người dùng thành công", Toast.LENGTH_SHORT).show();
            reloadUserFragment();
        }
        else {
            Toast.makeText(context, "Xoá người dùng thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void reloadUserFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new UserFragment());
        fragmentTransaction.commit();
    }
}