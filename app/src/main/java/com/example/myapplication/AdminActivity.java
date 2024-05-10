package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button btnViewPending = findViewById(R.id.btnViewPending);
        Button btnViewCompleted = findViewById(R.id.btnViewCompleted);

        btnViewPending.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, AdminBookingActivity.class);
            startActivity(intent);
        });

        btnViewCompleted.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, AdminBookingHistoryActivity.class);
            startActivity(intent);
        });
    }
}
