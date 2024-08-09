package com.example.bookticketapp.models;

public class Rating {
    private int id;
    private float rating;
    private int movieId;
    private int userId;

    public Rating(int id, float rating, int movieId, int userId) {
        this.id = id;
        this.rating = rating;
        this.movieId = movieId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
