package com.example.bookticketapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.bookticketapp.R;

import java.util.ArrayList;

public class StatisticFragment extends Fragment {
    private Spinner spnStat;
    private ListView lvStat;
    private ArrayAdapter<String> statOptionsAdapter;
    private ArrayAdapter<String> statAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);

        findViewByIds(view);

        // setup spinner
        String[] statOptions = {"Theo user", "Theo ngày", "Theo tháng", "Theo rạp"};
        statOptionsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, statOptions);
        statOptionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStat.setAdapter(statOptionsAdapter);

        // setup listview
        statAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, new ArrayList<>());
        lvStat.setAdapter(statAdapter);

        spnStat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateStatistics(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    private void findViewByIds(View view) {
        spnStat = view.findViewById(R.id.spnStatistic);
        lvStat = view.findViewById(R.id.lvStatistic);
    }

    private void updateStatistics(int i) {
    }
}