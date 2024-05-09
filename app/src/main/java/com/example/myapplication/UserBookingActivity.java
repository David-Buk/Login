package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserBookingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookingsAdapter bookingsAdapter;
    private DBHelper dbHelper;
    private Button refreshButton;
    private String email;  // To keep email accessible throughout the activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking);

        // Retrieve the email passed from the login activity
        email = getIntent().getStringExtra("email");

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshButton = findViewById(R.id.refreshButton);

        dbHelper = new DBHelper(this);

        // Initially fetch bookings for the given email
        refreshBookings();  // Fetch bookings initially and setup the adapter

        // Set up a button click listener to refresh bookings
        refreshButton.setOnClickListener(v -> refreshBookings());
    }

    private void refreshBookings() {
        List<Bookings> bookings = dbHelper.getBookingsByEmail(email);
        if (bookingsAdapter == null) {
            bookingsAdapter = new BookingsAdapter(bookings);
            recyclerView.setAdapter(bookingsAdapter);
        } else {
            bookingsAdapter.setBookings(bookings); // Assuming your adapter has a method to update the data
            bookingsAdapter.notifyDataSetChanged(); // Notify the adapter that the data set has changed
        }
    }
}
