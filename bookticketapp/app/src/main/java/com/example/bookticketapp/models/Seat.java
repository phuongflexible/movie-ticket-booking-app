package com.example.bookticketapp.models;

public class Seat {
    private int id;
    private String name;
    private boolean isVailable;

    public Seat(int id, String name, boolean isVailable) {
        this.id = id;
        this.name = name;
        this.isVailable = isVailable;
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

    public boolean isVailable() {
        return isVailable;
    }

    public void setVailable(boolean vailable) {
        isVailable = vailable;
    }
}
