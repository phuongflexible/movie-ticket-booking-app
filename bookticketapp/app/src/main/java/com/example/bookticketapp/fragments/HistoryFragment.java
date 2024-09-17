package com.example.bookticketapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.adapters.HistoryListviewAdapter;
import com.example.bookticketapp.dao.ReceiptHistoryQuery;
import com.example.bookticketapp.models.ReceiptHistory;

import java.util.List;

public class HistoryFragment extends Fragment {
    private TextView txtNoti;
    private ListView lvHistory;
    private HistoryListviewAdapter historyAdapter;
    private List<ReceiptHistory> historyList;
    private ReceiptHistoryQuery historyQuery;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);


        lvHistory = view.findViewById(R.id.lvHistory);
        txtNoti = view.findViewById(R.id.txtNoti_histotry);

        historyQuery = new ReceiptHistoryQuery(getContext());
        historyList = historyQuery.getHistoriesByUserId(2);   // lấy danh sách history theo user

        if (historyList.size() > 0) {
            historyAdapter = new HistoryListviewAdapter(getContext(), historyList);
            lvHistory.setAdapter(historyAdapter);
        } else {
            txtNoti.setVisibility(View.VISIBLE);
            lvHistory.setVisibility(View.GONE);
        }

        return view;
    }
}