package com.example.bookticketapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.dao.MovieQuery;
import com.example.bookticketapp.dao.UserQuery;
import com.example.bookticketapp.events.RatingSelectListener;
import com.example.bookticketapp.models.Movie;
import com.example.bookticketapp.models.Rating;
import com.example.bookticketapp.models.User;

import org.w3c.dom.Text;

import java.util.List;

public class RatingAdapter extends BaseAdapter {
    private List<Rating> listRatings;
    private Context context;
    private LayoutInflater inflater;
    MovieQuery movieQuery;
    UserQuery userQuery;
    private RatingSelectListener listener;

    public RatingAdapter(List<Rating> listRatings, Context context, RatingSelectListener listener) {
        this.listRatings = listRatings;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.movieQuery = new MovieQuery(context);
        this.userQuery = new UserQuery(context);
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return listRatings.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = this.inflater.inflate(R.layout.item_rating, null);
        TextView txtMovieName = view.findViewById(R.id.txtMovieName);
        TextView txtRating = view.findViewById(R.id.txtRating);
        TextView txtUserName = view.findViewById(R.id.txtUserName);

        Movie movie = movieQuery.getMovieById(listRatings.get(i).getMovieId());
        txtMovieName.setText("Phim: " + movie.getTitle());
        User user = userQuery.getUserById(listRatings.get(i).getUserId());
        txtUserName.setText("Người đánh giá: " + user.getName());
        txtRating.setText("Rating: " + String.valueOf(listRatings.get(i).getRating()));

        Button btnUpdateRating = view.findViewById(R.id.btnUpdateRating);
        btnUpdateRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updateRating(listRatings.get(i));
            }
        });

        Button btnDeleteRating = view.findViewById(R.id.btnDeleteRating);
        btnDeleteRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.deleteRating(listRatings.get(i));
            }
        });
        return view;
    }
}
