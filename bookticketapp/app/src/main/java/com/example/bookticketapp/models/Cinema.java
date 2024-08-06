package com.example.bookticketapp.models;

public class Cinema {
    private int id;
    private String name;
    private String address;
    private int cinemaImage;

    public Cinema(int id, String name, String address, int cinemaImage) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.cinemaImage = cinemaImage;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCinemaImage() {
        return cinemaImage;
    }

    public void setCinemaImage(int cinemaImage) {
        this.cinemaImage = cinemaImage;
    }
}
