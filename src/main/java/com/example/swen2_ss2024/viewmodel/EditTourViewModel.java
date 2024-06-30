package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.entity.RouteInfo;
import com.example.swen2_ss2024.entity.Tours;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.repository.TourRepository;
import com.example.swen2_ss2024.service.OpenRouteService;
import com.example.swen2_ss2024.service.TourListService;
import javafx.beans.property.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Objects;

public class EditTourViewModel {
    private final Publisher publisher;

    private final TourListService tourListService;

    private Tours currentTour;

    private final OpenRouteService routeService = new OpenRouteService();

    private static final Logger logger = LogManager.getLogger(EditTourViewModel.class);

    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty from = new SimpleStringProperty("");
    private final StringProperty to = new SimpleStringProperty("");
    private final StringProperty transportType = new SimpleStringProperty("");
    private final DoubleProperty distance = new SimpleDoubleProperty(0);
    private final DoubleProperty estimatedTime = new SimpleDoubleProperty(0);
    private final StringProperty imagePath = new SimpleStringProperty("");

    private final BooleanProperty editTourButtonDisabled = new SimpleBooleanProperty(true);

    // Private constructor
    public EditTourViewModel(Publisher publisher, TourListService tourListService) {
        this.publisher = publisher;
        this.tourListService = tourListService;

        name.addListener(((observable, oldValue, newValue) -> updateEditTourButtonDisabled()));
        description.addListener(((observable, oldValue, newValue) -> updateEditTourButtonDisabled()));
        from.addListener(((observable, oldValue, newValue) -> updateEditTourButtonDisabled()));
        to.addListener(((observable, oldValue, newValue) -> updateEditTourButtonDisabled()));
        transportType.addListener(((observable, oldValue, newValue) -> updateEditTourButtonDisabled()));
        distance.addListener(((observable, oldValue, newValue) -> updateEditTourButtonDisabled()));
        estimatedTime.addListener(((observable, oldValue, newValue) -> updateEditTourButtonDisabled()));
        imagePath.addListener(((observable, oldValue, newValue) -> updateEditTourButtonDisabled()));

        updateEditTourButtonDisabled();
    }

    private void updateEditTourButtonDisabled() {
        boolean anyFieldEmpty = name.get().isEmpty() || description.get().isEmpty() ||
                from.get().isEmpty() || to.get().isEmpty() ||
                transportType.get().isEmpty() || imagePath.get().isEmpty();

        System.out.println("Fields Empty: " + anyFieldEmpty); // Debug output
        System.out.println("Route Information: " + imagePath.get()); // Additional debug for image path
        editTourButtonDisabled.set(anyFieldEmpty);
    }

    public void setTour(Tours tour) {
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
            System.out.println("No tour selected to edit.");
            return;
        }

        Tours updatedTour = new Tours(
                name.get(),
                description.get(),
                from.get(),
                to.get(),
                transportType.get(),
                distance.get(),
                estimatedTime.get(),
                imagePath.get()
        );
        updatedTour.setId(currentTour.getId());

        getTourAttributesFromAPI(updatedTour);
        tourListService.updateTour(updatedTour);
        publisher.publish(Event.TOUR_UPDATED, updatedTour);
        System.out.println("Tour updated: " + updatedTour.getName());
    }

    private String transportTypeConverter(String transportType){
        return switch(transportType) {
            case "Car" -> "driving-car";
            case "Bicycle" -> "cycling-regular";
            case "Walk" -> "foot-walking";
            default -> throw new IllegalArgumentException();
        };
    }


    public void getTourAttributesFromAPI(Tours tour){
        String origin = tour.getFrom();
        String destination = tour.getTo();
        String transport = transportTypeConverter(tour.getTransportType());

        try{
            double[] originCoordinates = routeService.getCoordinates(origin);
            double[] destinationCoordinates = routeService.getCoordinates(destination);

            String res = routeService.getRouteFromCoordinates(Double.toString(originCoordinates[0]), Double.toString(originCoordinates[1]), Double.toString(destinationCoordinates[0]), Double.toString(destinationCoordinates[1]), transport);
            RouteInfo routeInfo = routeService.parseRoute(res);
            if(routeInfo != null){
                distance.set(routeInfo.getDistance());
                estimatedTime.set(routeInfo.getDuration());
                tour.setDistance(routeInfo.getDistance());
                tour.setEstimatedTime(routeInfo.getDuration());
            } else {
                logger.error("Failed to get Route!");
            }
        } catch (Exception e){
            logger.error("You encountered a problem!", e);
        }
    }

    public StringProperty nameProperty() { return name; }
    public StringProperty descriptionProperty() { return description; }
    public StringProperty fromProperty() { return from; }
    public StringProperty toProperty() { return to; }
    public StringProperty transportTypeProperty() { return transportType; }
    public DoubleProperty distanceProperty() { return distance; }
    public DoubleProperty estimatedTimeProperty() { return estimatedTime; }
    public StringProperty imagePathProperty() {
        return imagePath;
    }

    public BooleanProperty editTourButtonDisabledProperty() {
        return editTourButtonDisabled;
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
