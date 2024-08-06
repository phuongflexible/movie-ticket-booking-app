package com.example.bookticketapp.models;

import java.util.Date;

public class Movie {
    private int id;
    private String title;
    private String desciption;
    private int duration;
    private Date openingDay;
    private float rating;

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getOpeningDay() {
        return openingDay;
    }

    public void setOpeningDay(Date openingDay) {
        this.openingDay = openingDay;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Movie(int id, String title, String desciption, int duration, Date openingDay, float rating) {
        this.id = id;
        this.title = title;
        this.desciption = desciption;
        this.duration = duration;
        this.openingDay = openingDay;
        this.rating = rating;
    }
}
