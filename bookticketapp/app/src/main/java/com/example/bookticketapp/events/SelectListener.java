package com.example.bookticketapp.events;

import com.example.bookticketapp.models.Category;

public interface SelectListener {
    public void onUpdateButtonClicked(Category cate);
    public void onDeleteButtonClicked(Category cate);
}
