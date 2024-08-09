package com.example.bookticketapp.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Movie {
    private int id;
    private String title;
    private String desciption;
    private int categoryId;
    private int duration;
    private LocalDate openingDay;
    private float rating;
    private byte[] image;
    private List<Showtime> showtimes;
    private List<Rating> ratings;

    public Movie(int id, String title, String desciption, int categoryId, int duration, LocalDate openingDay, float rating, byte[] image) {
        this.id = id;
        this.title = title;
        this.desciption = desciption;
        this.categoryId = categoryId;
        this.duration = duration;
        this.openingDay = openingDay;
        this.rating = rating;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDate getOpeningDay() {
        return openingDay;
    }

    public void setOpeningDay(LocalDate openingDay) {
        this.openingDay = openingDay;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<Showtime> showtimes) {
        this.showtimes = showtimes;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
