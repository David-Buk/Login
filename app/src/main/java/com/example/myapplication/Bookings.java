package com.example.myapplication;

public class Bookings {
    private String userEmail;
    private String campus;
    private String date;
    private String time;

    public Bookings(String userEmail, String campus, String date, String time) {
        this.userEmail = userEmail;
        this.campus = campus;
        this.date = date;
        this.time = time;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getCampus() {
        return campus;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
