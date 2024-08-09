package com.example.bookticketapp.models;

import java.time.LocalDateTime;
import java.util.List;

public class Receipt {
    private int id;
    private float total;
    private LocalDateTime createdTime;
    private LocalDateTime paymentTime;
    private int userId;
    private int cinemaStaffId;
    private List<Ticket> tickets;

    public Receipt(int id, float total, LocalDateTime createdTime, LocalDateTime paymentTime, int userId, int cinemaStaffId) {
        this.id = id;
        this.total = total;
        this.createdTime = createdTime;
        this.paymentTime = paymentTime;
        this.userId = userId;
        this.cinemaStaffId = cinemaStaffId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCinemaStaffId() {
        return cinemaStaffId;
    }

    public void setCinemaStaffId(int cinemaStaffId) {
        this.cinemaStaffId = cinemaStaffId;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
