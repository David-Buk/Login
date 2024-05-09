package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdminBookingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookingsAdapter bookingsAdapter;
    private DBHelper dbHelper;
    private Spinner campusSpinner;
    private Button refreshButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_booking);

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        campusSpinner = findViewById(R.id.campusSpinner);
        refreshButton = findViewById(R.id.refreshButton);

        dbHelper = new DBHelper(this);
        List<Bookings> bookings = dbHelper.getAllBookings();

        bookingsAdapter = new BookingsAdapter(bookings);
        recyclerView.setAdapter(bookingsAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.campus_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campusSpinner.setAdapter(adapter);

        campusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterBookingsByCampus(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        refreshButton.setOnClickListener(v -> filterBookingsByCampus(campusSpinner.getSelectedItem().toString()));
    }

    private void filterBookingsByCampus(String campus) {
        List<Bookings> filteredBookings = dbHelper.getBookingsByCampus(campus);
        bookingsAdapter.updateData(filteredBookings);
    }
}