package com.example.swen2_ss2024.viewmodel;
import com.example.swen2_ss2024.models.Tour;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.service.TourListService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.swen2_ss2024.service.NewTourService ;
public class AddTourViewModel {
    private final TourListService tourListService;

    private final Publisher publisher;

    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty from = new SimpleStringProperty("");
    private final StringProperty to = new SimpleStringProperty("");
    private final StringProperty transportType = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");
    private final StringProperty estimatedTime = new SimpleStringProperty("");

    private final BooleanProperty addTourButtonDisabled = new SimpleBooleanProperty(true);

    private final ObservableList<String> addedRoutes = FXCollections.observableArrayList();

    public AddTourViewModel(Publisher publisher, TourListService tourListService){
        this.publisher = publisher;
        this.tourListService =tourListService;

        // Listens to changes in fields and update addButtonDisabled property
        name.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        description.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        from.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        to.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        transportType.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        distance.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        estimatedTime.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
    }

    private void updateAddTourButtonDisabled() {
        // Checks if any of the fields are empty
        addTourButtonDisabled.set(name.get().isEmpty() || description.get().isEmpty() ||
                from.get().isEmpty() || to.get().isEmpty() ||
                transportType.get().isEmpty() || distance.get().isEmpty() ||
                estimatedTime.get().isEmpty());
    }

    public void addTour() {
        if (!addTourButtonDisabled.get()) {
            System.out.println("Adding tour Button works");
            Tour tour = new Tour(name.get(), description.get(), from.get(), to.get(), transportType.get(), distance.get(), estimatedTime.get());
            tourListService.addTour(tour);
            publisher.publish(Event.TOUR_ADDED, tour);

            // Clears fields after publishing
            name.set("");
            description.set("");
            from.set("");
            to.set("");
            transportType.set("");
            distance.set("");
            estimatedTime.set("");
        }
    }



    // Getters for properties
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

    public BooleanProperty addTourButtonDisabledProperty() {
        return addTourButtonDisabled;
    }
}

