package com.example.bookticketapp.events;

import com.example.bookticketapp.models.Movie;

public interface MovieSelectListener {
    public void updateMovie(Movie movie);
    public void deleteMovie(Movie movie);
}
