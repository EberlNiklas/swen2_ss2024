package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.entity.Tours;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.ObjectSubscriber;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.service.NewTourService;
import com.example.swen2_ss2024.service.TourListService;
import com.example.swen2_ss2024.service.TourLogListService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourViewModel implements ObjectSubscriber {
    private final NewTourService newTourService;
    private final ObservableList<String> tourList = FXCollections.observableArrayList();
    private final IntegerProperty index = new SimpleIntegerProperty();
    private final Publisher publisher;
    private final TourListService tourListService;
    private final TourLogListService tourLogListService;
    private final BooleanProperty showAllDisabled = new SimpleBooleanProperty(true);

    public TourViewModel(Publisher publisher, TourListService tourListService, TourLogListService tourLogListService) {
        this.publisher = publisher;
        this.tourListService = tourListService;
        this.tourLogListService = tourLogListService;
        this.newTourService = new NewTourService();
        this.publisher.subscribe(Event.TOUR_ADDED, this);
        this.publisher.subscribe(Event.TOUR_UPDATED, this);
        this.publisher.subscribe(Event.SEARCH_RESULT, this);
        this.publisher.subscribe(Event.RESET_SEARCH, this);
        this.index.addListener((obs, oldVal, newVal) -> selectTourByIndex(newVal.intValue()));

        // Load initial data from database
        loadToursFromDatabase();
    }

    public void onAdd() {
        newTourService.loadFXML("add-tour-view.fxml");
    }

    public void onMore() {
        newTourService.loadFXML("edit-tour-view.fxml");
    }

    public void onShowAll() {
        System.out.println("Publishing RESET_SEARCH event"); // Debug line
        loadToursFromDatabase();
        publisher.publish(Event.RESET_SEARCH, new Object());
        setShowAllDisabled(true);
    }

    public Long getPKTour() {
        int tourIndex = index.get();
        if (tourIndex >= 0 && tourIndex < tourList.size()) {
            String tourName = tourList.get(tourIndex);
            Tours tour = tourListService.getTourByName(tourName);
            if (tour != null) {
                return tour.getId();
            }
        }
        return null;
    }

    public void selectTourByIndex(int index) {
        if (index >= 0 && index < tourList.size()) {
            String tourName = tourList.get(index);
            selectTour(tourName);
        }
    }

    public void loadToursFromDatabase() {
        System.out.println("Loading tours from database"); // Debug line
        tourList.clear();
        for (Tours tour : tourListService.getTours()) {
            System.out.println("Loaded tour: " + tour.getName()); // Debug line
            tourList.add(tour.getName());
        }
    }

    public void selectTour(String tourName) {
        Tours tour = tourListService.getTourByName(tourName);
        if (tour != null) {
            publisher.publish(Event.TOUR_SELECTED, tour);
        }
    }

    public void addToTourList(String tourName) {
        tourList.add(tourName);
    }

    public ObservableList<String> getTourNames() {
        return tourList;
    }

    public IntegerProperty selectedIndex() {
        return index;
    }

    public void deleteSelectedTour() {
        int tourIndex = index.get();
        if (tourIndex >= 0 && tourIndex < tourList.size()) {
            String tourName = tourList.get(tourIndex);
            if (tourListService.deleteTourByName(tourName)) {
                tourList.remove(tourIndex);
            }
        }
    }

    @Override
    public void notify(Object message) {
        if (message instanceof Tours) {
            Tours tour = (Tours) message;
            addToTourList(tour.getName());
        } else if (message instanceof Event && message == Event.RESET_SEARCH) {
            System.out.println("RESET_SEARCH event received"); // Debug line
            loadToursFromDatabase();
        }
    }

    public void updateTourListWithSearchResult(Tours searchResult) {
        tourList.clear();
        if (searchResult != null) {
            tourList.add(searchResult.getName());
            setShowAllDisabled(false);  // Enable the "Show All" button
        } else {
            setShowAllDisabled(true);  // Disable the "Show All" button if no search result
        }
    }

    public BooleanProperty showAllDisabledProperty() {
        return showAllDisabled;
    }

    public void setShowAllDisabled(boolean disabled) {
        this.showAllDisabled.set(disabled);
    }

    public Publisher getPublisher() {
        return publisher;
    }
}
