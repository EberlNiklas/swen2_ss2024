package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.ViewFactory;
import com.example.swen2_ss2024.models.Tour;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.ObjectSubscriber;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.service.NewTourService;
import com.example.swen2_ss2024.service.TourListService;
import com.example.swen2_ss2024.view.EditTourView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourViewModel implements ObjectSubscriber {
    private final NewTourService newTourService;
    private final ObservableList<Tour> tourList = FXCollections.observableArrayList();
    private final IntegerProperty index = new SimpleIntegerProperty();
    private TourListService tourListService;
    private Publisher publisher;

    public TourViewModel(Publisher publisher, TourListService tourListService) {
        this.publisher = publisher;
        this.tourListService = tourListService;
        this.newTourService = new NewTourService();
        this.publisher.subscribe(Event.TOUR_ADDED, this);
        this.publisher.subscribe(Event.TOUR_UPDATED, this);  // Subscribe to TOUR_UPDATED events
        this.index.addListener((obs, oldVal, newVal) -> selectTour(newVal.intValue()));
    }


    private void selectTour(int index) {
        if (index == -1) {
            System.out.println("No tour selected!");
        } else {
            Tour selectedTour = tourList.get(index);
            publisher.publish(Event.TOUR_SELECTED, selectedTour); // Publishes event with the selected tour
            System.out.println("Tour selected: " + selectedTour.getName());
        }
    }

    private void addToList(Tour tour) {
        tourList.add(tour);
    }

    public ObservableList<Tour> getTourList() {
        return tourList;
    }

    public IntegerProperty selectIndex() {
        return index;
    }

    public void delete() {
        int number = index.get();
        if (number >= 0 && number < tourList.size()) {
            Tour tour = tourList.get(number);
            if (tourListService.deleteTourByName(tour.getName())) {
                publisher.publish(Event.TOUR_DELETED, tour);  // Publishes the tour has been deleted
                tourList.remove(number);
                System.out.println("Tour deleted: " + tour.getName());
            }
        }
    }


    public void onAdd() {
        newTourService.loadFXML("add-tour-view.fxml");
    }

    @Override
    public void notify(Object message) {
        if (message instanceof Tour) {
            Tour tour = (Tour) message;
            switch (publisher.getCurrentEvent()) {
                case TOUR_ADDED:
                    addToList(tour);
                    tourListService.addTour(tour);
                    break;
                case TOUR_UPDATED:
                    updateTourInList(tour);
                    break;
            }
        }
    }

    private void updateTourInList(Tour updatedTour) {
        for (int i = 0; i < tourList.size(); i++) {
            Tour tour = tourList.get(i);
            if (tour.getName().equals(updatedTour.getName())) {  // Assuming name is a unique identifier
                tourList.set(i, updatedTour);
                break;
            }
        }
    }

    public void onMore() {
        // Obtains the selected Tour
        Tour selectedTour = tourList.get(index.get());
        EditTourView editView = (EditTourView) ViewFactory.getInstance().create(EditTourView.class);
        editView.setTour(selectedTour);
        newTourService.loadFXML("edit-tour-view.fxml");

        // Ensures that the tour details are updated after editing
        publisher.publish(Event.TOUR_SELECTED, selectedTour);
    }

}
