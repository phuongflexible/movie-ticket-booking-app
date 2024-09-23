package com.example.bookticketapp.events;

import com.example.bookticketapp.models.Cinema;

public interface CinemaSelectListener {
    public void updateCinema(Cinema cinema);
    public void deleteCinema(Cinema cinema);
}
