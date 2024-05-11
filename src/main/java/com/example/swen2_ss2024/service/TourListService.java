package com.example.swen2_ss2024.service;
import com.example.swen2_ss2024.models.Tour;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TourListService {
    private Set<Tour> tours = new HashSet<>();

    public void addTour(Tour tour) {
        System.out.println("Tour added: " + tour.getName());
        this.tours.add(tour);
    }

    public Set<Tour> getTours() {
        return tours;
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

    public boolean updateTour(Tour updatedTour) {
        Iterator<Tour> iterator = tours.iterator();
        while (iterator.hasNext()) {
            Tour existingTour = iterator.next();
            if (existingTour.getName().equals(updatedTour.getName())) {
                // Assuming Tour.equals() and Tour.hashCode() are based on the tour name
                iterator.remove(); // Remove the old tour
                tours.add(updatedTour); // Add the updated tour
                System.out.println("Tour updated: " + updatedTour.getName());
                return true;
            }
        }
        System.out.println("Failed to update, tour not found: " + updatedTour.getName());
        return false;
    }
}
