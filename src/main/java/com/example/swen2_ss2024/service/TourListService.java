package com.example.swen2_ss2024.service;
import com.example.swen2_ss2024.models.Tour;
import com.example.swen2_ss2024.database.Database;
import com.example.swen2_ss2024.models.Tour;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.example.swen2_ss2024.database.Database.connect;

public class TourListService {
    private Set<Tour> tours = new HashSet<>();

    public void addTour(Tour tour) {
        System.out.println("Tour added: " + tour.getName());
        this.tours.add(tour);
        try {
            Database.saveTour(tour);
        } catch (SQLException e) {
            System.out.println("Failed to save tour to database: " + e.getMessage());
        }
    }

    public Set<Tour> getTours() {
        return tours;
    }

    public static void saveTour(Tour tour) throws SQLException {
        String sql = "INSERT INTO Tour (\"name\", \"description\", \"from\", \"to\", \"transportType\", \"distance\", \"estimatedTime\", \"imagePath\") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tour.getName());
            pstmt.setString(2, tour.getDescription());
            pstmt.setString(3, tour.getFrom());
            pstmt.setString(4, tour.getTo());
            pstmt.setString(5, tour.getTransportType());
            pstmt.setString(6, tour.getDistance());
            pstmt.setString(7, tour.getEstimatedTime());
            pstmt.setString(8, tour.getImagePath());

            pstmt.executeUpdate();
        }
    }

    public Tour getTourByName(String name) {
        for (Tour tour : tours) {
            if (tour.getName().equals(name)) {
                return tour;
            }
        }
        return null; // Return null if no tour matches the given name
    }

    public boolean deleteTourByName(String tourName) {
        Iterator<Tour> iterator = tours.iterator();
        while (iterator.hasNext()) {
            Tour tour = iterator.next();
            if (tour.getName().equals(tourName)) {
                iterator.remove();
                System.out.println("Tour removed by name: " + tourName);
                return true;
            }
        }
        System.out.println("Tour not found by name: " + tourName);
        return false;
    }



    public void createTour(String name, String description, String from, String to, String transportType, String distance, String estimatedTime, String imagePath) {
        System.out.println("Creating tour..."); // Print statement for debugging
        Tour newTour = new Tour(name, description, from, to, transportType, distance, estimatedTime, imagePath);
        try {
            System.out.println("Saving tour..."); // Print statement for debugging
            saveTour(newTour);
            System.out.println("Tour saved."); // Print statement for debugging
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
