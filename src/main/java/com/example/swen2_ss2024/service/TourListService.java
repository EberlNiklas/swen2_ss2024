package com.example.swen2_ss2024.service;

import com.example.swen2_ss2024.database.Database;
import com.example.swen2_ss2024.models.Tour;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class TourListService {
    private Set<Tour> tours = new HashSet<>();


    public Set<Tour> getTours() {
        try {
            tours = Database.getAllTours();
        } catch (SQLException e) {
            System.out.println("Failed to load tours from database: " + e.getMessage());
        }
        return tours;
    }


    public void createTour(String name, String description, String from, String to, String transportType, String distance, String estimatedTime, String imagePath) {
        System.out.println("Creating tour..."); // Print statement for debugging
        Tour newTour = new Tour(name, description, from, to, transportType, distance, estimatedTime, imagePath);
        try {
            System.out.println("Saving tour..."); // Print statement for debugging
            Database.saveTour(newTour);
            System.out.println("Tour saved."); // Print statement for debugging
            tours.add(newTour);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
