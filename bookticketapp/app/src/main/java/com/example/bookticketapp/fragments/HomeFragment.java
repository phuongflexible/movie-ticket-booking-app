package com.example.bookticketapp.fragments;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.activities.MovieDetailsActivity;
import com.example.bookticketapp.adapters.MovieGridviewAdapter;
import com.example.bookticketapp.dao.MovieQuery;
import com.example.bookticketapp.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private ImageButton btnSearch;
    private EditText edtSearch;
    private TextView txtResult;
    private GridView gvMovies;
    private MovieGridviewAdapter movieAdapter;
    private List<Movie> movieList;
    private MovieQuery movieQuery;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        findViewByIds(view);

        movieQuery = new MovieQuery(getContext());

        // hiển thị danh sách phim
        movieList = movieQuery.getAllMovies();
        movieAdapter = new MovieGridviewAdapter(getContext(), movieList);
        gvMovies.setAdapter(movieAdapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMovies();
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
                    // ẩn bàn phím ảo
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);

                    searchMovies();
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    private void findViewByIds(View view) {
        gvMovies = view.findViewById(R.id.gvMovies);
        btnSearch = view.findViewById(R.id.btnSearchMovie);
        edtSearch = view.findViewById(R.id.edtSearchMovie);
        txtResult = view.findViewById(R.id.txtResult);
    }

    public void searchMovies() {
        String query = edtSearch.getText().toString();
        List<Movie> result = movieQuery.searchMoviesByTitle(query);

        movieList.clear();
        movieList.addAll(result);
        movieAdapter.notifyDataSetChanged();

        if (!query.isEmpty()) {
            txtResult.setVisibility(View.VISIBLE);
            txtResult.setText("Đã tìm thấy " + result.size() + " kết quả");
        } else {
            txtResult.setVisibility(View.GONE);
        }
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
}