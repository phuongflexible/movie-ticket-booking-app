package com.example.bookticketapp.models;

import java.util.List;

public class Seat {
    private int id;
    private String seatNumber;
    private int roomId;
    private boolean isAvailable;
    private List<Ticket> tickets;

    public Seat(int id, String seatNumber, int roomId, boolean isAvailable) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.roomId = roomId;
        this.isAvailable = isAvailable;
    }

    public Seat(String seatNumber, int roomId, boolean isAvailable) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.roomId = roomId;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
