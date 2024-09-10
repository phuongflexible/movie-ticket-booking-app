package com.example.bookticketapp.models;

import java.util.List;

public class User {
    private int id;
    private String password;
    private Role role;
    private String name;
    private String gender;
    private String phoneNumber;
    private String email;
    private List<Rating> ratings;
    private List<Receipt> receipts;

   public User(int id, String password, Role role, String name, String gender, String phoneNumber, String email) {
        this.id = id;
        this.password = password;
        this.role = role;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public User(String name, String gender, String email, String phoneNumber,  String password, Role role) {
        this.password = password;
        this.role = role;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /*public User(String email, String password, int roleId) {
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }*/

    public User(int id, Role role, String name, String gender, String phoneNumber, String email) {
        this.id = id;
        this.password = password;
        this.role = role;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public User(){
       
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }
}
