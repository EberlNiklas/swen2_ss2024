package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.entity.RouteInfo;
import com.example.swen2_ss2024.entity.Tours;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.repository.TourLogDatabaseRepository;
import com.example.swen2_ss2024.service.OpenRouteService;
import com.example.swen2_ss2024.service.TourListService;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;

public class AddTourViewModel {
    private final Publisher publisher;
    private final TourListService tourListService;
    private final OpenRouteService routeService = new OpenRouteService();

    private static final Logger logger = LogManager.getLogger(AddTourViewModel.class);

    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty from = new SimpleStringProperty("");
    private final StringProperty to = new SimpleStringProperty("");
    private final StringProperty transportType = new SimpleStringProperty("");
    private final DoubleProperty distance = new SimpleDoubleProperty(0);
    private final DoubleProperty estimatedTime = new SimpleDoubleProperty(0);
    private final StringProperty imagePath = new SimpleStringProperty("");

    private final BooleanProperty addTourButtonDisabled = new SimpleBooleanProperty(true);

    private final ObservableList<String> addedRoutes = FXCollections.observableArrayList();

    public AddTourViewModel(Publisher publisher, TourListService tourListService) {
        this.publisher = publisher;
        this.tourListService = tourListService;

        // Listens to changes in fields and update addButtonDisabled property
        name.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        description.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        from.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        to.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        transportType.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        distance.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        estimatedTime.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());
        imagePath.addListener((observable, oldValue, newValue) -> updateAddTourButtonDisabled());

        // Ensure initial state checks
        updateAddTourButtonDisabled();
    }

    private void updateAddTourButtonDisabled() {
        boolean anyFieldEmpty = name.get().isEmpty() || description.get().isEmpty() ||
                from.get().isEmpty() || to.get().isEmpty() ||
                transportType.get().isEmpty();

        addTourButtonDisabled.set(anyFieldEmpty);
    }

    public void addTour() {
        try{
        if (!addTourButtonDisabled.get()) {
            Tours tour = new Tours(
                    name.get(), description.get(), from.get(), to.get(),
                    transportType.get(), distance.get(), estimatedTime.get(), imagePath.get()
            );

            getTourAttributesFromAPI(tour);
            fetchAndSetMapImage(tour);
            tourListService.addTour(tour);
            publisher.publish(Event.TOUR_ADDED, tour);

            // Clears fields after publishing
            name.set("");
            description.set("");
            from.set("");
            to.set("");
            transportType.set("");
            distance.set(0);
            estimatedTime.set(0);
            imagePath.set("");

        }
        } catch (Exception e){
            logger.error("Failed to add tour" + e.getMessage());
        }
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

    public void fetchAndSetMapImage(Tours tour) throws IOException {
        try {
            BufferedImage mapImage = routeService.fetchMapForTour(tour, 16, 3); // Adjusted zoom level
            String imageURL = routeService.saveImage(mapImage);
            imagePath.set(imageURL);
            tour.setImagePath(imageURL);
        } catch (IOException e) {
            logger.error("Failed to fetch map image: " + e.getMessage());
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

    public DoubleProperty distanceProperty() {
        return distance;
    }

    public DoubleProperty estimatedTimeProperty() {
        return estimatedTime;
    }

    public BooleanProperty addTourButtonDisabledProperty() {
        return addTourButtonDisabled;
    }

    public StringProperty imagePathProperty() {
        return imagePath;
    }
}

