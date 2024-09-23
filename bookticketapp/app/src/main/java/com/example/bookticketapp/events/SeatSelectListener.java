package com.example.bookticketapp.events;

import com.example.bookticketapp.models.Seat;

public interface SeatSelectListener {
    void updateSeat(Seat seat);
    void deleteSeat(Seat seat);

}
