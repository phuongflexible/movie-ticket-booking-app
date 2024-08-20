package com.example.bookticketapp.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class Showtime {
    private int id;
    private int movieId;
    private int cinemaId;
    private LocalDate showDate;
    private LocalTime showtime;
    private List<Ticket> tickets;

    public Showtime(int id, int movieId, int cinemaId, LocalDate showDate, LocalTime showtime) {
        this.id = id;
        this.movieId = movieId;
        this.cinemaId = cinemaId;
        this.showDate = showDate;
        this.showtime = showtime;
    }

    // fake data -------------------------
    private String time;
    public Showtime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    //---------------------------------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public LocalDate getShowDate() {
        return showDate;
    }

    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;
    }

    public LocalTime getShowtime() {
        return showtime;
    }

    public void setShowtime(LocalTime showtime) {
        this.showtime = showtime;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
