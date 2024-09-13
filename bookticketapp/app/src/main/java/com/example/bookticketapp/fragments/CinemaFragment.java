package com.example.bookticketapp.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.adapters.CinemaListViewAdapter;
import com.example.bookticketapp.adapters.LocationSpinnerAdapter;
import com.example.bookticketapp.dao.CinemaQuery;
import com.example.bookticketapp.dao.LocationQuery;
import com.example.bookticketapp.models.Cinema;
import com.example.bookticketapp.models.Location;
import com.example.bookticketapp.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class CinemaFragment extends Fragment {
    private ImageButton btnSearch;
    private EditText edtSearch;
    private TextView txtResult;
    private Spinner spnLocation;
    private ListView lvCinema;
    private LocationSpinnerAdapter locationAdapter;
    private CinemaListViewAdapter cinemaAdapter;
    private List<Cinema> cinemaList;
    private CinemaQuery cinemaQuery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cinema, container, false);

        findViewByIds(view);
        initLocation();
        initCinemas();

        spnLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cinemaQuery = new CinemaQuery(getContext());
                Location selectedLocation = (Location) adapterView.getItemAtPosition(i);

                String locationName = selectedLocation.getName();
                if (locationName.equals("Toàn Quốc")) {    // nếu chọn "Toàn Quốc" thì lấy tất cả rạp
                    cinemaList.clear();
                    cinemaList.addAll(cinemaQuery.getAllCinemas());
                } else {                                   // chọn khu vực khác thì lấy danh sách rạp thuộc khu vực đó
                    cinemaList.clear();
                    cinemaList.addAll(cinemaQuery.getCinemasByLocationId(i));
                }

                cinemaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchCinemas();
            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                // khi ấn phím search hoặc done hoặc next trên phím ảo
                if (i == EditorInfo.IME_ACTION_SEARCH ||
                        i == EditorInfo.IME_ACTION_DONE ||
                        i == EditorInfo.IME_ACTION_NEXT ||
                        // khi ấn xuống phím enter trên phím vật lí
                        keyEvent.getKeyCode() == KeyEvent.ACTION_DOWN &&
                                keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    searchCinemas();

                    return true;
                }
                return false;
            }
        });


        return view;
    }

    private void findViewByIds(View view) {
        spnLocation = view.findViewById(R.id.spinnerLocation);
        lvCinema = view.findViewById(R.id.lvCinema);
        btnSearch = view.findViewById(R.id.btnSearchCinema);
        edtSearch = view.findViewById(R.id.edtSearchCinema);
        txtResult = view.findViewById(R.id.txtResult);
    }

    private void initLocation() {
        locationAdapter = new LocationSpinnerAdapter(getContext());
        spnLocation.setAdapter(locationAdapter);
    }

    private void initCinemas() {
        cinemaList = new ArrayList<>();

        cinemaAdapter = new CinemaListViewAdapter(getContext(), R.layout.item_cinema, cinemaList);
        lvCinema.setAdapter(cinemaAdapter);
    }

    public void searchCinemas() {
        String query = edtSearch.getText().toString();
        List<Cinema> result = cinemaQuery.searchCinemasByName(query);

        cinemaList.clear();
        cinemaList.addAll(result);
        cinemaAdapter.notifyDataSetChanged();

        if (!query.isEmpty() && result.size() > 0) {
            spnLocation.setVisibility(View.GONE);
            txtResult.setVisibility(View.VISIBLE);
            txtResult.setText("Đã tìm thấy " + result.size() + " kết quả");
        } else {
            spnLocation.setVisibility(View.VISIBLE);
            txtResult.setVisibility(View.GONE);
        }
    }
}