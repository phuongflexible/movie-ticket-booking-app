package com.example.bookticketapp.models;

import java.util.List;

public class ReceiptHistory {
    private int receiptId;
    private byte[] movieImage;
    private String movieTitle;
    private String showTime;
    private String showDate;
    private String cinema;
    private String room;
    private List<String> seatNumbers;
    private float totalPrice;
    private String createdTime;
    private String paymentTime;
    private String paymentMethod;

    public ReceiptHistory() {
    }

    public ReceiptHistory(int receiptId, byte[] movieImage, String movieTitle, String showTime, String showDate, String cinema, String room, List<String> seatNumbers, float totalPrice, String createdTime, String paymentTime, String paymentMethod) {
        this.receiptId = receiptId;
        this.movieImage = movieImage;
        this.movieTitle = movieTitle;
        this.showTime = showTime;
        this.showDate = showDate;
        this.cinema = cinema;
        this.room = room;
        this.seatNumbers = seatNumbers;
        this.totalPrice = totalPrice;
        this.createdTime = createdTime;
        this.paymentTime = paymentTime;
        this.paymentMethod = paymentMethod;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public byte[] getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(byte[] movieImage) {
        this.movieImage = movieImage;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public List<String> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(List<String> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
