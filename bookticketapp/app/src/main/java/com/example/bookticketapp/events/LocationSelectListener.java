package com.example.bookticketapp.events;

import com.example.bookticketapp.models.Location;

public interface LocationSelectListener {
    public void updateLocation(Location location);
    public void deleteLocation(Location location);
}
