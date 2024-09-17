package com.example.bookticketapp.fragments;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
    private AutoCompleteTextView autoTxtSearch;
    private TextView txtResult;
    private Spinner spnLocation;
    private ListView lvCinema;
    private LocationSpinnerAdapter locationAdapter;
    private CinemaListViewAdapter cinemaAdapter;
    private ArrayAdapter<String> cinemaNameAdapter;
    private List<Cinema> cinemaList;
    private List<String> cinemaNames;
    private CinemaQuery cinemaQuery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cinema, container, false);

        cinemaQuery = new CinemaQuery(getContext());

        findViewByIds(view);
        initLocation();
        initCinemas();
        initAutoSearch();

        spnLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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

        autoTxtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                // khi ấn phím search hoặc done hoặc next trên phím ảo
                if (i == EditorInfo.IME_ACTION_SEARCH ||
                        i == EditorInfo.IME_ACTION_DONE ||
                        i == EditorInfo.IME_ACTION_NEXT ||
                        // khi ấn xuống phím enter trên phím vật lí
                        keyEvent.getKeyCode() == KeyEvent.ACTION_DOWN &&
                                keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    // ẩn bàn phím ảo
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);

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
        autoTxtSearch = view.findViewById(R.id.autoTxtSearchCinema);
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

    private void initAutoSearch() {
        cinemaNames = cinemaQuery.getCinemaNames();
        cinemaNameAdapter = new ArrayAdapter<>(getContext(), R.layout.item_dropdown_auto, R.id.text1, cinemaNames);
        autoTxtSearch.setAdapter(cinemaNameAdapter);
        autoTxtSearch.setThreshold(1);
    }

    public void searchCinemas() {
        String query = autoTxtSearch.getText().toString();
        List<Cinema> result = cinemaQuery.searchCinemasByName(query);

        cinemaList.clear();
        cinemaList.addAll(result);
        cinemaAdapter.notifyDataSetChanged();

        // nếu có nhập ít nhất 1 ký tự, ẩn spnLocation, hiện txtResult
        if (!query.isEmpty()) {
            spnLocation.setVisibility(View.GONE);
            txtResult.setVisibility(View.VISIBLE);
            txtResult.setText("Đã tìm thấy " + result.size() + " kết quả");
        } else {
            spnLocation.setVisibility(View.VISIBLE);
            txtResult.setVisibility(View.GONE);
        }
    }
}