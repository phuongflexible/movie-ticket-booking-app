package com.example.bookticketapp.models;

import java.io.Serializable;

public class Ticket implements Serializable {
    private int id;
    private int showtimeId;
    private int seatId;
    private float price;
    private int receiptId;

    public Ticket(int id, int showtimeId, int seatId, float price, int receiptId) {
        this.id = id;
        this.showtimeId = showtimeId;
        this.seatId = seatId;
        this.price = price;
        this.receiptId = receiptId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }
}
