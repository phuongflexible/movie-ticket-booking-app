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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.activities.LoginActivity;
import com.example.bookticketapp.activities.SignUpActivity;
import com.example.bookticketapp.dao.UserQuery;
import com.example.bookticketapp.models.User;
import com.example.bookticketapp.utils.SessionManager;

public class AccountFragment extends Fragment {
    private TextView txtName, txtGender, txtPhone, txtEmail;
    private Button btnSignUp, btnLogIn, btnLogout;
    private LinearLayout linearSignupLogin, linearInfo;
    private UserQuery userQuery;
    private SessionManager sessionManager;

    public AccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        findViewByIds(view);

        sessionManager = new SessionManager(getContext());
        if (sessionManager.isLoggedIn()) {                // nếu đã đăng nhập
            linearSignupLogin.setVisibility(View.GONE);
            linearInfo.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.VISIBLE);

            userQuery = new UserQuery(getContext());

            int userId = sessionManager.getUserId();     // lấy id user đang đăng nhập
            User user = userQuery.getUserById(userId);

            txtName.setText(user.getName());
            txtGender.setText(user.getGender());
            txtPhone.setText(user.getPhoneNumber());
            txtEmail.setText(user.getEmail());

            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sessionManager.logout();

                    reloadAccountFragment();
                }
            });
        } else {          // nếu chưa đăng nhập
            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), SignUpActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });

            btnLogIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }

        return view;
    }

    private void findViewByIds(View view) {
        txtName = view.findViewById(R.id.txtName_account);
        txtGender = view.findViewById(R.id.txtGender_account);
        txtPhone = view.findViewById(R.id.txtPhone_account);
        txtEmail = view.findViewById(R.id.txtEmail_account);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        btnLogIn = view.findViewById(R.id.btnLogIn);
        btnLogout = view.findViewById(R.id.btnLogout_user);
        linearSignupLogin = view.findViewById(R.id.linearSignupLogin);
        linearInfo = view.findViewById(R.id.linearInfo_account);
    }

    private void reloadAccountFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new AccountFragment());
        fragmentTransaction.commit();
    }
}