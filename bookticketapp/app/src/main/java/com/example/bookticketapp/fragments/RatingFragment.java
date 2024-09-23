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
import android.widget.ListView;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.activities.UpdateRatingActivity;
import com.example.bookticketapp.adapters.RatingAdapter;
import com.example.bookticketapp.dao.RatingQuery;
import com.example.bookticketapp.events.RatingSelectListener;
import com.example.bookticketapp.models.Rating;

import java.util.ArrayList;
import java.util.List;

public class RatingFragment extends Fragment implements RatingSelectListener {

    Activity context;
    RatingQuery ratingQuery;
    RatingAdapter ratingAdapter;
    ListView lvRating;
    List<Rating> listRatings = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_rating, container, false);
        lvRating = view.findViewById(R.id.lvRating);
        ratingQuery = new RatingQuery(context);
        listRatings = ratingQuery.getAllRating();
        ratingAdapter = new RatingAdapter(listRatings, context, this);
        lvRating.setAdapter(ratingAdapter);
        return view;
    }

    @Override
    public void updateRating(Rating rating) {
        int id = rating.getId();
        float ratingNumber = rating.getRating();
        int movieId = rating.getMovieId();
        int userId = rating.getUserId();
        Intent intent = new Intent(context, UpdateRatingActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("ratingNumber", ratingNumber);
        intent.putExtra("movieId", movieId);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    @Override
    public void deleteRating(Rating rating) {
        Boolean delete = ratingQuery.deleteRating(rating.getId());
        if (delete == true)
        {
            Toast.makeText(context, "Xoá rating thành công", Toast.LENGTH_SHORT).show();
            reloadRatingFragment();
        }
        else
        {
            Toast.makeText(context, "Xoá rating thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void reloadRatingFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new RatingFragment());
        fragmentTransaction.commit();
    }
}