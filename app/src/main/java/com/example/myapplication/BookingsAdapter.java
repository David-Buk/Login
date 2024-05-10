package com.example.myapplication;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import java.util.ArrayList;


public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolder> {
    private List<Bookings> mData;
    private List<Bookings> mDataFiltered;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView emailTextView;
        private final TextView campusTextView;
        private final TextView dateTextView;
        private final TextView timeTextView;
        private final TextView statusTextView;

        public ViewHolder(View view) {
            super(view);
            emailTextView = view.findViewById(R.id.emailTextView);
            campusTextView = view.findViewById(R.id.campusTextView);
            dateTextView = view.findViewById(R.id.dateTextView);
            timeTextView = view.findViewById(R.id.timeTextView);
            statusTextView = view.findViewById(R.id.statusTextView);
        }
    }

    public BookingsAdapter(List<Bookings> data) {
        mData = data;
        mDataFiltered = new ArrayList<>(data); // copy full data
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bookings, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Bookings booking = mDataFiltered.get(position);
        holder.emailTextView.setText(booking.getEmail());
        holder.campusTextView.setText(booking.getCampus());
        holder.dateTextView.setText(booking.getDate());
        holder.timeTextView.setText(booking.getTime());
        holder.statusTextView.setText(booking.getStatus());
    }


    public void setBookings(List<Bookings> bookings) {
        this.mData = bookings;
        notifyDataSetChanged(); // Notify the adapter that the underlying data has changed
    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    public void updateData(List<Bookings> newData) {
        mData = new ArrayList<>(newData);
        mDataFiltered = new ArrayList<>(newData);
        notifyDataSetChanged();
    }
}
