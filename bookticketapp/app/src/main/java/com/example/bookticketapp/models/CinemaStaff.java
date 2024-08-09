package com.example.bookticketapp.models;

import java.util.List;

public class CinemaStaff {
    private int id;
    private int userId;
    private int cinemaId;
    private List<Receipt> receipts;

    public CinemaStaff(int id, int userId, int cinemaId) {
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

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }
}
