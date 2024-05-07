package com.example.swen2_ss2024;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

public class TourPlannerController {

    @FXML
    private ListView<?> tourList;

    @FXML
    protected void handleAddTour() {
        // Logic to add a tour
        System.out.println("Add tour clicked!");
    }

    @FXML
    protected void handleRemoveTour() {
        // Logic to remove a tour
        System.out.println("Remove tour clicked!");
    }

    @FXML
    protected void handleMoreOptions() {
        // Logic to open more options
        System.out.println("More options clicked!");
    }
}
