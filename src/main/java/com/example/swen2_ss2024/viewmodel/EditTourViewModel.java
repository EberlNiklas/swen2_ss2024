package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.entity.Tours;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.repository.TourRepository;
import com.example.swen2_ss2024.service.TourListService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class EditTourViewModel {
    private final Publisher publisher;

    private final TourListService tourListService;

    private Tours currentTour;


    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty from = new SimpleStringProperty("");
    private final StringProperty to = new SimpleStringProperty("");
    private final StringProperty transportType = new SimpleStringProperty("");
    private final StringProperty distance = new SimpleStringProperty("");
    private final StringProperty estimatedTime = new SimpleStringProperty("");
    private final StringProperty imagePath = new SimpleStringProperty("");

    private final BooleanProperty editTourButtonDisabled = new SimpleBooleanProperty(true);

    // Private constructor
    public EditTourViewModel(Publisher publisher, TourListService tourListService) {
        this.publisher = publisher;
        this.tourListService = tourListService;
    }

    public void editTour() {
        if (!editTourButtonDisabled.get()) {
            if (tourListService.selected()) {
                Tours toursSelected = tourListService.getSelectedTour();
                Tours newTour = new Tours(toursSelected.getName(), description.get(), from.get(), to.get(), transportType.get(), distance.get(), estimatedTime.get(), imagePath.get());
                Long id = toursSelected.getId();
                newTour.setId(id);
                tourListService.editTour(newTour);
            }
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
