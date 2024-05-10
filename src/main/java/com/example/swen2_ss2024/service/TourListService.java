package com.example.swen2_ss2024.service;
import com.example.swen2_ss2024.models.Tour ;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TourListService {
    private Set<Tour> tour = new HashSet<>();

    public void addTour(Tour tour) {
        System.out.println("Tour added: " + tour.getName());
        this.tour.add(tour);
    }

    public Set<Tour> getTours() {
        return tour;
    }

    public boolean deleteTourByName(String tourName) {
        Iterator<Tour> iterator = tour.iterator();
        while (iterator.hasNext()) {
            Tour tour = iterator.next();
            if (tour.getName().equals(tourName)) {
                iterator.remove();  // Remove the tour from the set
                System.out.println("Tour removed by name: " + tourName);
                return true;  // Return true if the tour was successfully removed
            }
        }
        System.out.println("Tour not found by name: " + tourName);
        return false;  // Return false if no tour with the specified name was found
    }
}
