package com.example.swen2_ss2024.models;

import java.time.LocalDate;

public class TourLog {
    private LocalDate date;
    private double duration; // in hours
    private double distance; // in kilometers
    private String notes;

    public TourLog(LocalDate date, double duration, double distance, String notes) {
        this.date = date;
        this.duration = duration;
        this.distance = distance;
        this.notes = notes;
    }

    // Getters and setters for all fields

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
//Placeholder, don´t know what it should look like later
    @Override
    public String toString() {
        return "Date: " + date + ", Duration: " + duration + " hours, Distance: " + distance + " km, Notes: " + notes;
    }
}
