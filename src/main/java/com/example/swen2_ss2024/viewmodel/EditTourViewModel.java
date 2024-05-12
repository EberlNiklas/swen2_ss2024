package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.models.Tour;
import com.example.swen2_ss2024.service.TourListService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.stage.Stage;

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
            System.out.println("Error: No current tour set when trying to edit.");
            return;
        }

        // Set properties which are bound to UI fields
        currentTour.setName(name.get());
        currentTour.setDescription(description.get());
        currentTour.setFrom(from.get());
        currentTour.setTo(to.get());
        currentTour.setTransportType(transportType.get());
        currentTour.setDistance(distance.get());
        currentTour.setEstimatedTime(estimatedTime.get());

        // Update image path separately
        String updatedImagePath = imagePath.get();
        System.out.println("Updated Route Information: " + updatedImagePath); // Check if the image path is updated
        publisher.publish(Event.IMAGE_PATH_UPDATED, updatedImagePath);

        // Notify all observers about the change
        publisher.publish(Event.TOUR_UPDATED, currentTour);
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
        if (path != null && !path.isEmpty()) {
            imagePath.set(path);
        } else {
            imagePath.set(null);
        }
    }

}
