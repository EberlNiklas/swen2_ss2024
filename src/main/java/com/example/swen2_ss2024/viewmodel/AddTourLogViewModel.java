package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.models.Tour;
import com.example.swen2_ss2024.models.TourLog;
import com.example.swen2_ss2024.service.TourListService;
import com.example.swen2_ss2024.service.TourLogListService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddTourLogViewModel {

    private final TourLogListService tourLogListService;

    private final Publisher publisher;

    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty date = new SimpleStringProperty("");
    private final StringProperty duration = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");
    private final StringProperty comment = new SimpleStringProperty("");
    private final StringProperty rating = new SimpleStringProperty("");
    private final StringProperty difficulty = new SimpleStringProperty("");

    private final BooleanProperty addTourLogButtonDisabled = new SimpleBooleanProperty(true);

    private final ObservableList<String> addedRoutes = FXCollections.observableArrayList();

    public AddTourLogViewModel(Publisher publisher, TourLogListService tourLogListService){
        this.publisher = publisher;
        this.tourLogListService = tourLogListService;

        // Listens to changes in fields and update addButtonDisabled property
        name.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        date.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        duration.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        distance.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        comment.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        rating.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        difficulty.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        // Ensure initial state checks
        updateAddTourButtonDisabled();
    }



    private void updateAddTourButtonDisabled() {
        boolean anyFieldEmpty = name.get().isEmpty() || date.get().isEmpty() ||
                duration.get().isEmpty() || distance.get().isEmpty() ||
                comment.get().isEmpty() || rating.get().isEmpty() || difficulty.get().isEmpty();

        System.out.println("Fields Empty: " + anyFieldEmpty); // Debug output
        addTourLogButtonDisabled.set(anyFieldEmpty);
    }


    public void addTourLog() {
        if (!addTourLogButtonDisabled.get()) {
            System.out.println("Adding tourLog Button works");
            TourLog tourLog = new TourLog(
                    name.get(), date.get(), duration.get(), distance.get(),
                    comment.get(), rating.get(), difficulty.get()
            );
            tourLogListService.addTourLog(tourLog);
            publisher.publish(Event.TOUR_LOG_ADDED, tourLog);

            // Clears fields after publishing
            name.set("");
            date.set("");
            duration.set("");
            distance.set("");
            comment.set("");
            rating.set("");
        }
    }



    // Getters for properties
    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty dateProperty() {
        return date;
    }

    public StringProperty durationProperty() {
        return duration;
    }

    public StringProperty distanceProperty() {
        return distance;
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public StringProperty ratingProperty() {
        return rating;
    }

    public StringProperty difficultyProperty() {
        return difficulty;
    }
    public BooleanProperty addTourLogButtonDisabledProperty() {
        return addTourLogButtonDisabled;
    }

}
