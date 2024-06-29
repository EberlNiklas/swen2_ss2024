package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.entity.TourLog;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.ObjectSubscriber;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.service.TourListService;
import com.example.swen2_ss2024.service.TourLogListService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddTourLogViewModel implements ObjectSubscriber {

    private final TourLogListService tourLogListService;

    private final TourListService tourListService;

    private final Publisher publisher;

    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty date = new SimpleStringProperty("");
    private final StringProperty info = new SimpleStringProperty("");
    private final StringProperty rating = new SimpleStringProperty("");

    private final BooleanProperty addTourLogButtonDisabled = new SimpleBooleanProperty(true);

    public AddTourLogViewModel(Publisher publisher, TourLogListService tourLogListService, TourListService tourListService){
        this.publisher = publisher;
        this.tourLogListService = tourLogListService;
        this.tourListService = tourListService;

        // Listens to changes in fields and update addButtonDisabled property
        name.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        date.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        rating.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        info.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        // Ensure initial state checks
        updateAddTourButtonDisabled();
    }



    private void updateAddTourButtonDisabled() {
        boolean anyFieldEmpty = name.get().isEmpty() || date.get().isEmpty() ||
                rating.get().isEmpty() || info.get().isEmpty();

        addTourLogButtonDisabled.set(anyFieldEmpty);
    }


    public void addTourLog() {
        if (!addTourLogButtonDisabled.get()) {
            if (tourListService.selected()) {
                TourLog tourLog = new TourLog(name.get(), date.get(), rating.get(), info.get());
                tourLog.setTour(tourListService.getSelectedTour());
                tourLogListService.addTourLog(tourLog);
                publisher.publish(Event.TOUR_LOG_ADDED, tourLog);


                // Clear fields after publishing
                name.set("");
                date.set("");
                rating.set("");
                info.set("");


            }
        }
    }



    // Getters for properties
    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty dateProperty() {
        return date;
    }

    public StringProperty infoProperty() {
        return info;
    }

    public StringProperty ratingProperty() {
        return rating;
    }

    public BooleanProperty addTourLogButtonDisabledProperty() {
        return addTourLogButtonDisabled;
    }

    @Override
    public void notify(Object message) {}
}
