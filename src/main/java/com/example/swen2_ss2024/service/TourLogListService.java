package com.example.swen2_ss2024.service;

import com.example.swen2_ss2024.models.Tour;
import com.example.swen2_ss2024.models.TourLog;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TourLogListService {

    private Set<TourLog> tourLogs = new HashSet<>();

    public void addTourLog(TourLog tourLog) {
        System.out.println("Tour added: " + tourLog.getName());
        this.tourLogs.add(tourLog);
    }

    public Set<TourLog> getTourLogs() {
        return tourLogs;
    }

    public TourLog getTourLogsByName(String name) {
        for (TourLog tourLog : tourLogs) {
            if (tourLog.getName().equals(name)) {
                return tourLog;
            }
        }
        return null; // Return null if no tour matches the given name
    }
    public boolean deleteTourLogByName(String tourLogName) {
        Iterator<TourLog> iterator = tourLogs.iterator();
        while (iterator.hasNext()) {
            TourLog tourLog = iterator.next();
            if (tourLog.getName().equals(tourLogName)) {
                iterator.remove();
                System.out.println("Tour removed by name: " + tourLogName);
                return true;
            }
        }
        System.out.println("Tour not found by name: " + tourLogName);
        return false;
    }

    public boolean updateTour(TourLog currentTourLog, TourLog updatedTourLog) {
        if (tourLogs.contains(currentTourLog)) {
            tourLogs.remove(currentTourLog);
            tourLogs.add(updatedTourLog);
            System.out.println("TourLog updated: " + updatedTourLog.getName());
            return true;
        }
        System.out.println("Failed to update, tour not found: " + currentTourLog.getName());
        return false;
    }
}
