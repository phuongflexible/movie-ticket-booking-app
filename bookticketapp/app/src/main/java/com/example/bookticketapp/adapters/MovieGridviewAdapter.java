package com.example.bookticketapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.activities.MovieDetailsActivity;
import com.example.bookticketapp.models.Movie;
import com.example.bookticketapp.utils.ImageUtils;

import java.util.List;

public class MovieGridviewAdapter extends BaseAdapter {
    private Context context;
    private List<Movie> movieList;

    public MovieGridviewAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        CardView cardMovie;
        ImageView imgMovie;
        TextView txtTitle, txtRating;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_movie_gridview, null);

            holder = new ViewHolder();
            holder.cardMovie = view.findViewById(R.id.cardMovie);
            holder.imgMovie = view.findViewById(R.id.imgMovie_list);
            holder.txtTitle = view.findViewById(R.id.txtMovieTitle_list);
            holder.txtRating = view.findViewById(R.id.txtRating_list);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Movie movie = movieList.get(i);

        Bitmap bitmap = ImageUtils.byteArrayToBitmap(movie.getImage());
        holder.imgMovie.setImageBitmap(bitmap);
        holder.txtTitle.setText(movie.getTitle());
        holder.txtRating.setText(movie.getRating() + "");

        holder.cardMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int movieId = movie.getId();

                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("movieId", movieId);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
