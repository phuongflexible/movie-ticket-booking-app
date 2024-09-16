package com.example.bookticketapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.activities.LoginActivity;
import com.example.bookticketapp.adapters.HistoryListviewAdapter;
import com.example.bookticketapp.dao.ReceiptHistoryQuery;
import com.example.bookticketapp.models.ReceiptHistory;
import com.example.bookticketapp.utils.SessionManager;

import java.util.List;

public class HistoryFragment extends Fragment {
    private TextView txtNoti, txtLogin;
    private ListView lvHistory;
    private LinearLayout linearLogin;
    private HistoryListviewAdapter historyAdapter;
    private List<ReceiptHistory> historyList;
    private ReceiptHistoryQuery historyQuery;
    private SessionManager sessionManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        sessionManager = new SessionManager(getContext());

        lvHistory = view.findViewById(R.id.lvHistory);
        txtNoti = view.findViewById(R.id.txtNoti_histotry);
        linearLogin = view.findViewById(R.id.linearLogin_history);
        txtLogin = view.findViewById(R.id.txtLogin_histotry);

        if (sessionManager.isLoggedIn()) {     // nếu đã đăng nhập
            int userId = sessionManager.getUserId();    // lấy id user đang đăng nhập

            historyQuery = new ReceiptHistoryQuery(getContext());
            historyList = historyQuery.getHistoriesByUserId(userId);   // lấy danh sách history theo user

            if (historyList.size() > 0) {
                historyAdapter = new HistoryListviewAdapter(getContext(), historyList);
                lvHistory.setAdapter(historyAdapter);
            } else {
                txtNoti.setVisibility(View.VISIBLE);
                lvHistory.setVisibility(View.GONE);
            }
        } else {   // chưa đăng nhập thì hiện yêu cầu đăng nhập
            linearLogin.setVisibility(View.VISIBLE);
            txtNoti.setVisibility(View.GONE);
            lvHistory.setVisibility(View.GONE);

            txtLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }

        return view;
    }
}