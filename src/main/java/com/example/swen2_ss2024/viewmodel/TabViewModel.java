package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.ObjectSubscriber;
import com.example.swen2_ss2024.event.Publisher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleObjectProperty;
import com.example.swen2_ss2024.models.Tour;
import javafx.beans.property.ObjectProperty;
import javafx.scene.image.Image;

import java.util.Arrays;

import static com.example.swen2_ss2024.event.Event.*;

public class TabViewModel implements ObjectSubscriber {
    private SimpleObjectProperty<Image> routeImage = new SimpleObjectProperty<>();
    private Publisher publisher;
    private SimpleObjectProperty<Tour> selectedTour = new SimpleObjectProperty<>();
    private ObservableList<String> tourDetails = FXCollections.observableArrayList();

    public TabViewModel(Publisher publisher) {
        this.publisher = publisher;
        publisher.subscribe(TOUR_SELECTED, this);
        publisher.subscribe(TOUR_DELETED, this);
        publisher.subscribe(TOUR_UPDATED, this);
        publisher.subscribe(IMAGE_PATH_UPDATED, this);
    }

    @Override
    public void notify(Object message) {
        if (message instanceof Tour) {
            Tour tour = (Tour) message;
            switch (publisher.getCurrentEvent()) {
                case TOUR_SELECTED:
                    selectedTour.set(tour);
                    updateTourDetails();
                    break;
                case TOUR_UPDATED: // Updates tour details on tour edit event
                    selectedTour.set(tour);
                    updateTourDetails();
                    break;
                case TOUR_DELETED:
                    clearTourDetails();
                    clearRouteImage(); // Clear route image on tour delete event
                    break;
            }
        } else if (message instanceof String && publisher.getCurrentEvent() == Event.IMAGE_PATH_UPDATED) {
            // Update route image when the image path is updated
            String imagePath = (String) message;
            if (imagePath != null && !imagePath.isEmpty()) {
                routeImage.set(new Image(imagePath));
            } else {
                clearRouteImage(); // Clear route image if image path is empty
            }
        }
    }

    // method to update tour details
    private void updateTourDetails() {
        tourDetails.clear();
        Tour tour = selectedTour.get();
        if (tour != null) {
            tourDetails.addAll(Arrays.asList(
                    "Name: " + tour.getName(),
                    "Description: " + tour.getDescription(),
                    "From: " + tour.getFrom(),
                    "To: " + tour.getTo(),
                    "Transport Type: " + tour.getTransportType(),
                    "Distance: " + tour.getDistance(),
                    "Estimated Time: " + tour.getEstimatedTime(),
                    "Image Path: " + tour.getImagePath()
            ));
            routeImage.set(new Image(tour.getImagePath()));

        }

    }
    public ObjectProperty<Image> routeImageProperty() {
        return routeImage;
    }

    private void clearTourDetails() {
        tourDetails.clear();
    }

    public ObservableList<String> getTourDetails() {
        return tourDetails;
    }

    // method to clear route image
    private void clearRouteImage() {
        routeImage.set(null);
    }
}
