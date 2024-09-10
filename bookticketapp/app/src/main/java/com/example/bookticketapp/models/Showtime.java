package com.example.bookticketapp.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Showtime implements Serializable {
    private int id;
    private int movieId;
    private int cinemaId;
    private Calendar showDate;
    private Calendar showtime;
    private List<Ticket> tickets;

    public Showtime(int id, int movieId, int cinemaId, Calendar showDate, Calendar showtime) {
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

    public Calendar getShowDate() {
        return showDate;
    }

    public void setShowDate(Calendar showDate) {
        this.showDate = showDate;
    }

    public Calendar getShowtime() {
        return showtime;
    }

    public void setShowtime(Calendar showtime) {
        this.showtime = showtime;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
