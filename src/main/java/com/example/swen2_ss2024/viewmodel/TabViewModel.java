package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.entity.Tours;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.ObjectSubscriber;
import com.example.swen2_ss2024.event.Publisher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.util.Arrays;

public class TabViewModel implements ObjectSubscriber {
    private final SimpleObjectProperty<Image> routeImage = new SimpleObjectProperty<>();
    private final Publisher publisher;
    private final ObservableList<String> tourDetails = FXCollections.observableArrayList();

    public TabViewModel(Publisher publisher) {
        this.publisher = publisher;
        this.publisher.subscribe(Event.TOUR_SELECTED, this);
        this.publisher.subscribe(Event.TOUR_DELETED, this);
        this.publisher.subscribe(Event.TOUR_UPDATED, this);
        this.publisher.subscribe(Event.IMAGE_PATH_UPDATED, this);
    }

    @Override
    public void notify(Object message) {
        if (message instanceof Tours) {
            Tours tour = (Tours) message;
            switch (publisher.getCurrentEvent()) {
                case TOUR_SELECTED:
                case TOUR_UPDATED:
                    updateTourDetails(tour);
                    break;
                case TOUR_DELETED:
                    clearTourDetails();
                    clearRouteImage();
                    break;
            }
        } else if (message instanceof String && publisher.getCurrentEvent() == Event.IMAGE_PATH_UPDATED) {
            String imagePath = (String) message;
            if (imagePath != null && !imagePath.isEmpty()) {
                routeImage.set(new Image(imagePath));
            } else {
                clearRouteImage();
            }
        }
    }

    private void updateTourDetails(Tours tour) {
        tourDetails.clear();
        tourDetails.addAll(Arrays.asList(
                "Name: " + tour.getName(),
                "Description: " + tour.getDescription(),
                "From: " + tour.getFrom(),
                "To: " + tour.getTo(),
                "Transport Type: " + tour.getTransportType(),
                "Distance: " + tour.getDistance(),
                "Estimated Time: " + tour.getEstimatedTime(),
                "Route Information: " + tour.getImagePath()
        ));
        routeImage.set(new Image(tour.getImagePath()));
    }

    private void clearTourDetails() {
        tourDetails.clear();
    }

    private void clearRouteImage() {
        routeImage.set(null);
    }

    public ObservableList<String> getTourDetails() {
        return tourDetails;
    }

    public SimpleObjectProperty<Image> routeImageProperty() {
        return routeImage;
    }
}
