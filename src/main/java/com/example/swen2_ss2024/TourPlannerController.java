package com.example.swen2_ss2024;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.swen2_ss2024.models.Tour;

public class TourPlannerController {

    @FXML
    private ListView<Tour> tourList;

    private ObservableList<Tour> tours = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        tourList.setItems(tours);

    }

    @FXML
    protected void handleAddTour() {
        // Placeholder to see if the tour gets added to the list
        Tour newTour = new Tour("Mystery Tour", "Test");
        tours.add(newTour);
    }

    @FXML
    protected void handleRemoveTour() {
        Tour selected = tourList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            tours.remove(selected);
        } else {
            showAlert("Please select a tour to delete.");
        }
    }

    @FXML
    protected void handleMoreOptions() {
        // For later
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
