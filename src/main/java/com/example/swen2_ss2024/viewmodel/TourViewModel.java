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
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.List;
import java.util.stream.Collectors;

public class TourViewModel implements ObjectSubscriber {
    private final NewTourService newTourService;
    private final ObservableList<Tours> tourList = FXCollections.observableArrayList();
    private final IntegerProperty index = new SimpleIntegerProperty();
    private final Publisher publisher;
    private final TourListService tourListService;
    private final TourLogListService tourLogListService;
    private final BooleanProperty showAllDisabled = new SimpleBooleanProperty(true);

    private final EditTourViewModel editTourViewModel;

    public TourViewModel(Publisher publisher, TourListService tourListService, TourLogListService tourLogListService) {
        this.publisher = publisher;
        this.tourListService = tourListService;
        this.tourLogListService = tourLogListService;
        this.newTourService = new NewTourService();
        this.editTourViewModel = new EditTourViewModel(publisher, tourListService);
        this.publisher.subscribe(Event.TOUR_ADDED, this);
        this.publisher.subscribe(Event.TOUR_UPDATED, this);
        this.publisher.subscribe(Event.SEARCH_RESULT, this);
        this.publisher.subscribe(Event.RESET_SEARCH, this);

        this.index.addListener((obs, oldVal, newVal) -> selectTour(newVal.intValue()));

        // Load initial data from database
        loadToursFromDatabase();
    }

    public void loadToursFromDatabase() {
        tourList.clear();
        tourList.addAll(tourListService.getTours());
    }

    public IntegerProperty selectIndex() {
        return index;
    }

    public void selectTour(int index) {
        if (index == -1) {
            System.out.println("No tour selected!");
        } else {
            Tours selectedTour = tourList.get(index);
            tourListService.setSelected(true);
            tourListService.setSelectedTour(selectedTour);
            publisher.publish(Event.TOUR_SELECTED, selectedTour); // Publishes event with the selected tour
            System.out.println("Tour selected: " + selectedTour.getName());
            Long dbindex = getPKTour(); // Ensure this is not null

            if (dbindex != null) {
                tourLogListService.getTourLogsByTourName(selectedTour.getName());
                publisher.publish(Event.SELECTED_TOUR_CHANGED, dbindex);
            }
        }
    }

    public Long getPKTour() {
        int tourIndex = index.get();
        if (tourIndex >= 0 && tourIndex < tourList.size()) {
            Tours tour = tourList.get(tourIndex);
            if (tour != null) {
                return tour.getId();
            }
        }
        return null;
    }

    public ObservableList<Tours> getTourList() {
        return tourList;
    }

    public void delete() {
        int number = index.get();
        if (number >= 0 && number < tourList.size()) {
            Tours tour = tourList.get(number);
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
        if (message instanceof Tours) {
            Tours tour = (Tours) message;
            switch (publisher.getCurrentEvent()) {
                case TOUR_ADDED:
                    tourList.add(tour);
                    break;
                case TOUR_UPDATED:
                    for (int i = 0; i < tourList.size(); i++) {
                        if (tourList.get(i).getId() == tour.getId()) {
                            tourList.set(i, tour);
                            break;
                        }
                    }
                    break;
                case SEARCH_RESULT:
                    tourList.clear();
                    tourList.add(tour);
                    break;
                case RESET_SEARCH:
                    loadToursFromDatabase();
                    break;
            }
        }
    }

    public void filterTours(String searchTerm) {
        List<Tours> allTours = tourListService.getTours();
        tourList.clear();
        tourList.addAll(allTours.stream()
                .filter(tour -> tour.getName().equalsIgnoreCase(searchTerm))
                .collect(Collectors.toList()));
    }

    public void onMore() {
        // Obtains the selected Tour
        Tours selectedTour = tourList.get(index.get());
        EditTourView editView = (EditTourView) ViewFactory.getInstance().create(EditTourView.class);
        editView.setTour(selectedTour);
        newTourService.loadFXML("edit-tour-view.fxml");
    }

    public void setTour(String name) {
        Tours tour = tourListService.getTourByName(name);
        if (tour != null) {
            tourList.add(tour);
        }
    }

    public void showAllTours(Object o) {
        System.out.println("Showing all tours"); // Debug statement
        tourList.clear();
        tourList.addAll(tourListService.getTours());
    }

    public void onShowAll() {
        System.out.println("Publishing RESET_SEARCH event"); // Debug line
        loadToursFromDatabase();
        publisher.publish(Event.RESET_SEARCH, new Object());
        setShowAllDisabled(true);
    }

    public void updateTourListWithSearchResult(Tours searchResult) {
        tourList.clear();
        if (searchResult != null) {
            tourList.add(searchResult);
            setShowAllDisabled(false);  // Enable the "Show All" button
        } else {
            setShowAllDisabled(true);  // Disable the "Show All" button if no search result
        }
    }

    public ObservableList<String> getTourNames() {
        return FXCollections.observableArrayList(
                tourList.stream()
                        .map(Tours::getName)
                        .collect(Collectors.toList())
        );
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
