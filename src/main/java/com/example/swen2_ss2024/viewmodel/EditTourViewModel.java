package com.example.swen2_ss2024.viewmodel;
import com.example.swen2_ss2024.models.Tour;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.service.TourListService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class EditTourViewModel {
    private final TourListService tourListService;
    private final Publisher publisher;

    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty from = new SimpleStringProperty("");
    private final StringProperty to = new SimpleStringProperty("");
    private final StringProperty transportType = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");
    private final StringProperty estimatedTime = new SimpleStringProperty("");
    private final BooleanProperty editTourButtonDisabled = new SimpleBooleanProperty(true);

    public EditTourViewModel(Publisher publisher, TourListService tourListService) {
        this.publisher = publisher;
        this.tourListService = tourListService;

        // Update the disabled state of the edit button based on the fields' content
        name.addListener((observable, oldValue, newValue) -> updateEditTourButtonDisabled());
        description.addListener((observable, oldValue, newValue) -> updateEditTourButtonDisabled());
        from.addListener((observable, oldValue, newValue) -> updateEditTourButtonDisabled());
        to.addListener((observable, oldValue, newValue) -> updateEditTourButtonDisabled());
        transportType.addListener((observable, oldValue, newValue) -> updateEditTourButtonDisabled());
        distance.addListener((observable, oldValue, newValue) -> updateEditTourButtonDisabled());
        estimatedTime.addListener((observable, oldValue, newValue) -> updateEditTourButtonDisabled());
    }

    private void updateEditTourButtonDisabled() {
        // Check if any of the fields are empty to enable/disable the edit button
        editTourButtonDisabled.set(name.get().isEmpty() || description.get().isEmpty() ||
                from.get().isEmpty() || to.get().isEmpty() ||
                transportType.get().isEmpty() || distance.get().isEmpty() ||
                estimatedTime.get().isEmpty());
    }

    public void editTour() {
        Tour updatedTour = new Tour(name.get(), description.get(), from.get(), to.get(), transportType.get(), distance.get(), estimatedTime.get());
        if(tourListService.updateTour(updatedTour)) {
            publisher.publish(Event.TOUR_UPDATED, updatedTour);
            System.out.println("Tour updated successfully.");
        }
    }

    // Property getters for the FXML bindings
    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty fromProperty() {
        return from;
    }

    public StringProperty toProperty() {
        return to;
    }

    public StringProperty transportTypeProperty() {
        return transportType;
    }

    public StringProperty distanceProperty() {
        return distance;
    }

    public StringProperty estimatedTimeProperty() {
        return estimatedTime;
    }

    public BooleanProperty editTourButtonDisabledProperty() {
        return editTourButtonDisabled;
    }
}
