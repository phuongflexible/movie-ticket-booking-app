package com.example.bookticketapp.models;

import java.util.Calendar;
import java.util.List;

public class Receipt {
    private int id;
    private float total;
    private Calendar createdTime;
    private Calendar paymentTime;
    private int paymentMethodId;
    private int userId;
    private List<Ticket> tickets;

    public Receipt(int id, float total, Calendar createdTime, Calendar paymentTime, int paymentMethodId, int userId) {
        this.id = id;
        this.total = total;
        this.createdTime = createdTime;
        this.paymentTime = paymentTime;
        this.paymentMethodId = paymentMethodId;
        this.userId = userId;
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

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }

    public Calendar getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Calendar paymentTime) {
        this.paymentTime = paymentTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
