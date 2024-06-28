package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.ViewFactory;
import com.example.swen2_ss2024.entity.Tours;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.ObjectSubscriber;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.service.NewTourService;
import com.example.swen2_ss2024.service.TourListService;
import com.example.swen2_ss2024.service.TourLogListService;
import com.example.swen2_ss2024.view.EditTourView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

public class TourViewModel implements ObjectSubscriber {
    private final NewTourService newTourService;
    private final ObservableList<String> tourList = FXCollections.observableArrayList();
    private final IntegerProperty index = new SimpleIntegerProperty();
    private final Publisher publisher;
    private final TourListService tourListService;

    private final TourLogListService tourLogListService;

    public TourViewModel(Publisher publisher, TourListService tourListService, TourLogListService tourLogListService) {
        this.publisher = publisher;
        this.tourListService = tourListService;
        this.tourLogListService = tourLogListService;
        this.newTourService = new NewTourService();
        this.publisher.subscribe(Event.TOUR_ADDED, this);
        this.publisher.subscribe(Event.TOUR_UPDATED, this);
        this.publisher.subscribe(Event.SEARCH_RESULT, this);
        this.index.addListener((obs, oldVal, newVal) -> selectTour(newVal.intValue()));

        // Load initial data from database
        loadToursFromDatabase();
    }

    private void loadToursFromDatabase() {
        tourList.clear();
        for(Tours tour : tourListService.getTours())
            tourList.add(tour.getName());
    }

    public void onAdd() {
        newTourService.loadFXML("add-tour-view.fxml");
    }

    public void onMore() {
        // Obtains the selected Tour
        newTourService.loadFXML("edit-tour-view.fxml");
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

    private void selectTour(int index) {
            String tourName = tourList.get(index);
            Tours tour = tourListService.getTourByName(tourName);
            tourListService.setSelected(true);
            tourListService.setSelectedTour(tour);

            Long dbindex = getPKTour(); // Ensure this is not null

            if (dbindex != null) {
                tourLogListService.getTourLogsByTourName(tourName);
                publisher.publish(Event.SELECTED_TOUR_CHANGED, dbindex);
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
        }
    }
}
