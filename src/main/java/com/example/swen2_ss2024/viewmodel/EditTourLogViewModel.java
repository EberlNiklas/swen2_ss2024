package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.entity.TourLog;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.service.TourListService;
import com.example.swen2_ss2024.service.TourLogListService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EditTourLogViewModel {

    private static EditTourLogViewModel instance;
    private final Publisher publisher;
    private final TourLogListService tourLogListService;

    private final TourListService tourListService;

    private TourLog currentTourLog;


    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty date = new SimpleStringProperty("");
    private final StringProperty duration = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");
    private final StringProperty comment = new SimpleStringProperty("");
    private final StringProperty rating = new SimpleStringProperty("");
    private final StringProperty difficulty = new SimpleStringProperty("");

    private final StringProperty info = new SimpleStringProperty("");
    private final BooleanProperty modifyTourLogButtonDisabled = new SimpleBooleanProperty(true);

    // Private constructor
    public EditTourLogViewModel(Publisher publisher, TourLogListService tourLogListService, TourListService tourListService) {

        this.publisher = publisher;
        this.tourLogListService = tourLogListService;
        this.tourListService = tourListService;

        // Listen to changes in fields and update addButtonDisabled property
        name.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
        date.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
        duration.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
        distance.addListener((observable, oldValue, newValue) -> updateAddTourLogButtonDisabled());
    }




    private void updateAddTourLogButtonDisabled() {
        // Check if any of the fields are empty
        modifyTourLogButtonDisabled.set(name.get().isEmpty() || date.get().isEmpty() ||
                duration.get().isEmpty() || distance.get().isEmpty());
    }


    public void modifyTourLog() {
        if(!modifyTourLogButtonDisabled.get()){
            if(tourLogListService.selected()){
                TourLog currentlySelected = tourLogListService.getSelectedTourLog();
                Long id = currentlySelected.getId();
                TourLog newTourLog = new TourLog(name.get(), date.get(), rating.get(), info.get(), distance.get(), duration.get());
                newTourLog.setId(id);
                tourLogListService.editTourLog(newTourLog);

            }

        }
    }

    public StringProperty nameProperty() { return name; }
    public StringProperty dateProperty() { return date; }
    public StringProperty durationProperty() { return duration; }
    public StringProperty distanceProperty() { return distance; }
    public StringProperty ratingProperty() { return rating; }
    public StringProperty infoProperty() { return info; }
}
