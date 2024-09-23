package com.example.bookticketapp.models;

import java.util.List;

public class Room {
    private int id;
    private String name;
    private int cinemaId;
    private List<Seat> seats;

    public Room(int id, String name, int cinemaId) {
        this.id = id;
        this.name = name;
        this.cinemaId = cinemaId;
    }

    public Room(String name, int cinemaId) {
        this.name = name;
        this.cinemaId = cinemaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
