package com.example.bookticketapp.models;

import androidx.annotation.NonNull;

public class PaymentMethod {
    private int id;
    private String name;

    public PaymentMethod(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public PaymentMethod(String name) {
        this.id = id;
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
