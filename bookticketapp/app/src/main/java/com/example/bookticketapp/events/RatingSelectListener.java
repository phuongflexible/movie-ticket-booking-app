package com.example.bookticketapp.events;

import com.example.bookticketapp.models.Rating;

public interface RatingSelectListener {
    void updateRating(Rating rating);
    void deleteRating(Rating rating);
}
