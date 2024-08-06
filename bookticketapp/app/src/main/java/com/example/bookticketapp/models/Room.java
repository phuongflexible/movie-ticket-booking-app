package com.example.bookticketapp.models;

public class Room {
    private int id;
    private String name;
    private int cinemaId;

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

    public Room(int id, String name, int cinemaId) {
        this.id = id;
        this.name = name;
        this.cinemaId = cinemaId;
    }
}
