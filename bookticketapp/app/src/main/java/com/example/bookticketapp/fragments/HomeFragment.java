package com.example.bookticketapp.fragments;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.activities.MovieDetailsActivity;
import com.example.bookticketapp.adapters.MovieGridviewAdapter;
import com.example.bookticketapp.dao.MovieQuery;
import com.example.bookticketapp.models.Movie;
import com.example.bookticketapp.utils.ImageUtils;
import com.example.bookticketapp.utils.PasswordUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private ImageButton btnSearch;
    private AutoCompleteTextView autoTxtSearch;
    private TextView txtResult, txtNewReleases, txtTopSelling, txtNowShowing;
    private HorizontalScrollView cvNewReleases, cvTopSelling;
    private GridView gvMovies;
    private LinearLayout linearNewReleases, linearTopSelling, linearContainer;
    private MovieGridviewAdapter movieAdapter;
    private ArrayAdapter<String> movieTitleAdapter;
    private List<Movie> movieList;
    private List<String> movieTitles;
    private MovieQuery movieQuery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        movieQuery = new MovieQuery(getContext());
        
        findViewByIds(view);
        initNewReleases();
        initTopSelling();
        initMovieList();
        initAutoSearch();


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMovies();
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
        autoTxtSearch = view.findViewById(R.id.autoTxtSearchMovie);
        txtResult = view.findViewById(R.id.txtResult);
        linearNewReleases = view.findViewById(R.id.linearNewReleases);
        linearTopSelling = view.findViewById(R.id.linearTopSelling);
        linearContainer = view.findViewById(R.id.linearContainer);
        txtNewReleases = view.findViewById(R.id.txtNewReleases);
        txtTopSelling = view.findViewById(R.id.txtTopSelling);
        txtNowShowing = view.findViewById(R.id.txtNowShowing);
        cvNewReleases = view.findViewById(R.id.cvNewReleases);
        cvTopSelling = view.findViewById(R.id.cvTopSelling);
    }

    private void initMovieList() {
        movieList = movieQuery.getAllMovies();
        movieAdapter = new MovieGridviewAdapter(getContext(), movieList);
        gvMovies.setAdapter(movieAdapter);
    }

    private void initNewReleases() {
        List<Movie> newReleases = movieQuery.getNewReleases(4);

        for (Movie movie : newReleases) {
            View view = getLayoutInflater().inflate(R.layout.item_movie_gridview, linearNewReleases, false);

            CardView cardMovie = view.findViewById(R.id.cardMovie);
            ImageView imgMovie = view.findViewById(R.id.imgMovie_list);
            TextView txtTitle = view.findViewById(R.id.txtMovieTitle_list);
            TextView txtRating = view.findViewById(R.id.txtRating_list);

            String formatString = String.format("%.1f", movie.getRating());
            Bitmap bitmap = ImageUtils.byteArrayToBitmap(movie.getImage());

            imgMovie.setImageBitmap(bitmap);
            txtTitle.setText(movie.getTitle());
            txtRating.setText(formatString);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, // Width
                    LinearLayout.LayoutParams.WRAP_CONTENT  // Height
            );
            params.setMargins(8, 8, 8, 8);

            view.setLayoutParams(params);

            cardMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int movieId = movie.getId();

                    Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
                    intent.putExtra("movieId", movieId);
                    getActivity().startActivity(intent);
                }
            });

            linearNewReleases.addView(view);
        }
    }

    private void initTopSelling() {
        List<Movie> topSelling = movieQuery.getTopSellingMovies(3);

        for (Movie movie : topSelling) {
            View view = getLayoutInflater().inflate(R.layout.item_movie_gridview, linearTopSelling, false);

            CardView cardMovie = view.findViewById(R.id.cardMovie);
            ImageView imgMovie = view.findViewById(R.id.imgMovie_list);
            TextView txtTitle = view.findViewById(R.id.txtMovieTitle_list);
            TextView txtRating = view.findViewById(R.id.txtRating_list);

            String formatString = String.format("%.1f", movie.getRating());
            Bitmap bitmap = ImageUtils.byteArrayToBitmap(movie.getImage());

            imgMovie.setImageBitmap(bitmap);
            txtTitle.setText(movie.getTitle());
            txtRating.setText(formatString);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, // Width
                    LinearLayout.LayoutParams.WRAP_CONTENT  // Height
            );
            params.setMargins(8, 8, 8, 8);

            view.setLayoutParams(params);

            cardMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int movieId = movie.getId();

                    Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
                    intent.putExtra("movieId", movieId);
                    getActivity().startActivity(intent);
                }
            });

            linearTopSelling.addView(view);
        }
    }

    private void initAutoSearch() {
        movieTitles = movieQuery.getMovieNames();
        movieTitleAdapter = new ArrayAdapter<>(getContext(), R.layout.item_dropdown_auto, R.id.text1, movieTitles);
        autoTxtSearch.setAdapter(movieTitleAdapter);
        autoTxtSearch.setThreshold(1);
    }

    public void searchMovies() {
        String query = autoTxtSearch.getText().toString();
        List<Movie> result = movieQuery.searchMoviesByTitle(query);

        movieList.clear();
        movieList.addAll(result);
        movieAdapter.notifyDataSetChanged();

        if (!query.isEmpty()) {
            txtResult.setVisibility(View.VISIBLE);
            txtResult.setText("Đã tìm thấy " + result.size() + " kết quả");
            txtNewReleases.setVisibility(View.GONE);
            txtTopSelling.setVisibility(View.GONE);
            txtTopSelling.setVisibility(View.GONE);
            cvNewReleases.setVisibility(View.GONE);
            cvTopSelling.setVisibility(View.GONE);
        } else {
            txtResult.setVisibility(View.GONE);
            txtNewReleases.setVisibility(View.VISIBLE);
            txtTopSelling.setVisibility(View.VISIBLE);
            txtTopSelling.setVisibility(View.VISIBLE);
            cvNewReleases.setVisibility(View.VISIBLE);
            cvTopSelling.setVisibility(View.VISIBLE);
        }
    }
}