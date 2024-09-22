package com.example.bookticketapp.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.adapters.CinemaListViewAdapter;
import com.example.bookticketapp.dao.CinemaQuery;
import com.example.bookticketapp.models.Cinema;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CinemaAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CinemaAdminFragment extends Fragment {

    Activity context;
    CinemaQuery cinemaQuery;
    List<Cinema> listCinemas;
    CinemaListViewAdapter cinemaAdapter;
    ListView lvCinemas;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CinemaAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CinemaAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CinemaAdminFragment newInstance(String param1, String param2) {
        CinemaAdminFragment fragment = new CinemaAdminFragment();
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
        View view = inflater.inflate(R.layout.fragment_cinema_admin, container, false);

        cinemaQuery = new CinemaQuery(context);
        listCinemas = new ArrayList<>();
        listCinemas = cinemaQuery.getAllCinemas();
        cinemaAdapter = new CinemaListViewAdapter(context, R.layout.item_cinema_admin, listCinemas);

        lvCinemas = view.findViewById(R.id.listViewCinema);
        lvCinemas.setAdapter(cinemaAdapter);

        return view;
    }


}