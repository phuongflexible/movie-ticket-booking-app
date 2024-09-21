package com.example.bookticketapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.bookticketapp.R;
import com.example.bookticketapp.dao.CategoryQuery;
import com.example.bookticketapp.events.MovieSelectListener;
import com.example.bookticketapp.models.Movie;
import com.example.bookticketapp.utils.DatetimeUtils;
import com.example.bookticketapp.utils.ImageUtils;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private ArrayList<Movie> listMovies = new ArrayList<>();
    private Context context;
    private CategoryQuery categoryQuery;
    //private MovieSelectListener listener;

    public MovieAdapter(ArrayList<Movie> listMovies, Context context/*, MovieSelectListener listener*/) {
        this.listMovies = listMovies;
        this.context = context;
        this.categoryQuery = new CategoryQuery(context);
        //this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = listMovies.get(position);
        holder.txtFilmTitle.setText(movie.getTitle());
        holder.txtFilmDescription.setText("Mô tả: " + movie.getDesciption());
        holder.txtFilmCategory.setText("Thể loại: " + categoryQuery.findCategoryName(movie.getCategoryId()));
        holder.txtFilmDuration.setText("Thời lượng: " + String.valueOf(movie.getDuration()) + " phút");
        holder.txtFilmOpeningDay.setText("Khởi chiếu: " + DatetimeUtils.calendarToString(movie.getOpeningDay()));
        holder.txtFilmRating.setText("Rating: " + String.valueOf(movie.getRating()));
        holder.imageFilm.setImageBitmap(ImageUtils.byteArrayToBitmap(movie.getImage()));
        /*holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updateMovie(listMovies.get(holder.getAdapterPosition()));
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.deleteMovie(listMovies.get(holder.getAdapterPosition()));
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtFilmTitle;
        private TextView txtFilmDescription;
        private TextView txtFilmCategory;
        private TextView txtFilmDuration;
        private TextView txtFilmOpeningDay;
        private TextView txtFilmRating;
        private ImageView imageFilm;
        //private Button btnUpdate;
        //private Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFilmTitle = itemView.findViewById(R.id.txtFilmTitle);
            txtFilmDescription = itemView.findViewById(R.id.txtFilmDescription);
            txtFilmCategory = itemView.findViewById(R.id.txtFilmCategory);
            txtFilmDuration = itemView.findViewById(R.id.txtFilmDuration);
            txtFilmOpeningDay = itemView.findViewById(R.id.txtFilmOpeningDay);
            txtFilmRating = itemView.findViewById(R.id.txtFilmRating);
            imageFilm = itemView.findViewById(R.id.imageFilm);
            //btnUpdate = itemView.findViewById(R.id.btnUpdateMovie);
            //btnDelete = itemView.findViewById(R.id.btnDeleteMovie);
        }
    }
}
