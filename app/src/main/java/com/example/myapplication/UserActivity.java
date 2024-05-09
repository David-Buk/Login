package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    private Button btnBook;
    private Button btnBookings;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        // Retrieve the email passed from the login activity
        email = getIntent().getStringExtra("email");

        btnBook = findViewById(R.id.btnBook);
        btnBookings = findViewById(R.id.btnBookings);

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, BookingActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        btnBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, UserBookingActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }
}
