package com.example.myapplication;

public class Bookings {
    private String email;
    private String campus;
    private String date;
    private String time;

    public Bookings(String email, String campus, String date, String time) {
        this.email = email;
        this.campus = campus;
        this.date = date;
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public String getCampus() {return campus; }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
