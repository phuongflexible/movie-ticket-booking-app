package com.example.bookticketapp.events;

import com.example.bookticketapp.models.Room;

public interface RoomSelectListener {
    void updateRoom(Room room);
    void deleteRoom(Room room);
}
