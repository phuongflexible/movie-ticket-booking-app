package com.example.bookticketapp.events;

import com.example.bookticketapp.models.User;

public interface UserSelectListener {
    public void onUpdateUserButtonClicked(User user);
    public void onDeleteUserButtonClicked(User user);
}
