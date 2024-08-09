package com.example.bookticketapp.models;

public class CinemaManager {
    private int id;
    private int userId;
    private int cinemaId;

    public CinemaManager(int id, int userId, int cinemaId) {
        this.id = id;
        this.userId = userId;
        this.cinemaId = cinemaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }
}
