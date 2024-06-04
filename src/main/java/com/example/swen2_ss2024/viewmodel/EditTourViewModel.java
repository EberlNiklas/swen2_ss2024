package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.models.Tour;
import com.example.swen2_ss2024.service.TourListService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.stage.Stage;

import java.sql.SQLException;

public class EditTourViewModel {
    private static EditTourViewModel instance;
    private final Publisher publisher;
    private final TourListService tourListService;

    private Tour currentTour;


    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty from = new SimpleStringProperty("");
    private final StringProperty to = new SimpleStringProperty("");
    private final StringProperty transportType = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");
    private final StringProperty estimatedTime = new SimpleStringProperty("");
    private final StringProperty imagePath = new SimpleStringProperty("");

    // Private constructor
    private EditTourViewModel(Publisher publisher, TourListService tourListService) {
        this.publisher = publisher;
        this.tourListService = tourListService;
    }

    // instance method with dependency parameters
    public static synchronized EditTourViewModel getInstance(Publisher publisher, TourListService tourListService) {
        if (instance == null) {
            instance = new EditTourViewModel(publisher, tourListService);
        }
        return instance;
    }

    public void setTour(Tour tour) {
        if (tour == null) {
            System.out.println("Attempt to set null tour.");
            return;
        }
        this.currentTour = tour;
        name.set(tour.getName());
        description.set(tour.getDescription());
        from.set(tour.getFrom());
        to.set(tour.getTo());
        transportType.set(tour.getTransportType());
        distance.set(tour.getDistance());
        estimatedTime.set(tour.getEstimatedTime());
        System.out.println("Setting current tour: " + tour.getName());
    }

    public void editTour() {
        if (currentTour == null) {
            System.out.println("No tour selected to edit.");
            return;
        }

        Tour updatedTour = new Tour(
                name.get(),
                description.get(),
                from.get(),
                to.get(),
                transportType.get(),
                distance.get(),
                estimatedTime.get(),
                imagePath.get()
        );
        updatedTour.setId(currentTour.getId());

        try {
            com.example.swen2_ss2024.database.Database.updateTour(updatedTour);
            publisher.publish(Event.TOUR_UPDATED, updatedTour);
            System.out.println("Tour updated: " + updatedTour.getName());
        } catch (SQLException e) {
            System.out.println("Failed to update tour: " + e.getMessage());
        }
    }





    public StringProperty nameProperty() { return name; }
    public StringProperty descriptionProperty() { return description; }
    public StringProperty fromProperty() { return from; }
    public StringProperty toProperty() { return to; }
    public StringProperty transportTypeProperty() { return transportType; }
    public StringProperty distanceProperty() { return distance; }
    public StringProperty estimatedTimeProperty() { return estimatedTime; }
    public StringProperty imagePathProperty() {
        return imagePath;
    }

    public void setImagePath(String path) {
        if (path != null && !path.isEmpty() && !path.equals(imagePath.get())) {
            imagePath.set(path);
            publisher.publish(Event.IMAGE_PATH_UPDATED, path);
            System.out.println("Updated Route Information: " + path);
        } else if (path == null || path.isEmpty()) {
            imagePath.set(null);
            publisher.publish(Event.IMAGE_PATH_UPDATED, null);
        }
    }

}
