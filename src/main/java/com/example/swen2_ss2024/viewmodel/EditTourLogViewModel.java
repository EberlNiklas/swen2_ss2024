package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.models.Tour;
import com.example.swen2_ss2024.models.TourLog;
import com.example.swen2_ss2024.service.TourListService;
import com.example.swen2_ss2024.service.TourLogListService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EditTourLogViewModel {

    private static EditTourLogViewModel instance;
    private final Publisher publisher;
    private final TourLogListService tourListService;

    private TourLog currentTourLog;


    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty date = new SimpleStringProperty("");
    private final StringProperty duration = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");
    private final StringProperty comment = new SimpleStringProperty("");
    private final StringProperty rating = new SimpleStringProperty("");
    private final StringProperty difficulty = new SimpleStringProperty("");

    // Private constructor
    private EditTourLogViewModel(Publisher publisher, TourLogListService tourLogListService) {
        this.publisher = publisher;
        this.tourListService = tourLogListService;
    }

    // instance method with dependency parameters
    public static synchronized EditTourLogViewModel getInstance(Publisher publisher, TourLogListService tourLogListService) {
        if (instance == null) {
            instance = new EditTourLogViewModel(publisher, tourLogListService);
        }
        return instance;
    }

    public void setTourLog(TourLog tourLog) {
        if (tourLog == null) {
            System.out.println("Attempt to set null tour log.");
            return;
        }
        this.currentTourLog = tourLog;
        name.set(tourLog.getName());
        date.set(tourLog.getDate());
        duration.set(tourLog.getDuration());
        distance.set(tourLog.getDistance());
        comment.set(tourLog.getComment());
        rating.set(tourLog.getRating());
        difficulty.set(tourLog.getDifficulty());
        System.out.println("Setting current tour log: " + tourLog.getName());
    }

    public void editTourLog() {
        if (currentTourLog == null) {
            System.out.println("Error: No current tour log set when trying to edit.");
            return;
        }

        // Set properties which are bound to UI fields
        currentTourLog.setName(name.get());
        currentTourLog.setDate(date.get());
        currentTourLog.setDuration(duration.get());
        currentTourLog.setDistance(distance.get());
        currentTourLog.setComment(comment.get());
        currentTourLog.setRating(rating.get());
        currentTourLog.setDifficulty(difficulty.get());

        // Notify all observers about the change
        publisher.publish(Event.TOUR_LOG_EDITED, currentTourLog);
    }






    public StringProperty nameProperty() { return name; }
    public StringProperty dateProperty() { return date; }
    public StringProperty durationProperty() { return duration; }
    public StringProperty distanceProperty() { return distance; }
    public StringProperty commentProperty() { return comment; }
    public StringProperty ratingProperty() { return rating; }
    public StringProperty difficultyProperty() { return difficulty; }
}
