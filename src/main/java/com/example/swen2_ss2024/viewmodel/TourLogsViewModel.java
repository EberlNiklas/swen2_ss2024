package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.ViewFactory;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.ObjectSubscriber;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.models.Tour;
import com.example.swen2_ss2024.models.TourLog;
import com.example.swen2_ss2024.service.NewTourLogService;
import com.example.swen2_ss2024.service.NewTourService;
import com.example.swen2_ss2024.service.TourListService;
import com.example.swen2_ss2024.service.TourLogListService;
import com.example.swen2_ss2024.view.EditTourLogView;
import com.example.swen2_ss2024.view.EditTourView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourLogsViewModel implements ObjectSubscriber {

    private final NewTourLogService newTourLogService;
    private final ObservableList<TourLog> tourLogList = FXCollections.observableArrayList();
    private final IntegerProperty index = new SimpleIntegerProperty();
    private TourLogListService tourLogListService;
    private Publisher publisher;

    public TourLogsViewModel(Publisher publisher, TourLogListService tourLogListService) {
        this.publisher = publisher;
        this.tourLogListService = tourLogListService;
        this.newTourLogService = new NewTourLogService();
        this.publisher.subscribe(Event.TOUR_LOG_ADDED, this);
        this.publisher.subscribe(Event.TOUR_LOG_EDITED, this);
        this.index.addListener((obs, oldVal, newVal) -> selectTour(newVal.intValue()));
    }


    private void selectTour(int index) {
        if (index == -1) {
            System.out.println("No tour selected!");
        } else {
            TourLog selectedTourLog = tourLogList.get(index);
            publisher.publish(Event.TOUR_SELECTED, selectedTourLog); // Publishes event with the selected tour
            System.out.println("TourLog selected: " + selectedTourLog.getName());
        }
    }

    private void addToList(TourLog tourLog) {
        tourLogList.add(tourLog);
    }

    public ObservableList<TourLog> getTourList() {
        return tourLogList;
    }

    public IntegerProperty selectIndex() {
        return index;
    }

    public void delete() {
        int number = index.get();
        if (number >= 0 && number < tourLogList.size()) {
            TourLog tourLog = tourLogList.get(number);
            if (tourLogListService.deleteTourLogByName(tourLog.getName())) {
                publisher.publish(Event.TOUR_LOG_DELETED, tourLog);  // Publishes the tour has been deleted
                tourLogList.remove(number);
                System.out.println("TourLog deleted: " + tourLog.getName());
            }
        }
    }


    public void onAdd() {
        newTourLogService.loadFXML("add-tour-log-view.fxml");
    }

    @Override
    public void notify(Object message) {
        if (message instanceof TourLog) {
            TourLog tourLog = (TourLog) message;
            switch (publisher.getCurrentEvent()) {
                case TOUR_LOG_ADDED:
                    addToList(tourLog);
                    tourLogListService.addTourLog(tourLog);
                    break;
                case TOUR_LOG_EDITED:
                    updateTourInList(tourLog);
                    break;
            }
        }
    }

    private void updateTourInList(TourLog updatedTourLog) {
        for (int i = 0; i < tourLogList.size(); i++) {
            TourLog tourLog = tourLogList.get(i);
            if (tourLog.getName().equals(updatedTourLog.getName())) {  // Assuming name is a unique identifier
                tourLogList.set(i, updatedTourLog);
                break;
            }
        }
    }

    public void onMore() {
        // Obtains the selected Tour
        TourLog selectedTourLog = tourLogList.get(index.get());
        EditTourLogView editView = (EditTourLogView) ViewFactory.getInstance().create(EditTourLogView.class);
        editView.setTourLog(selectedTourLog);
        newTourLogService.loadFXML("edit-tour-log-view.fxml");

        // Ensures that the tour details are updated after editing
        publisher.publish(Event.TOUR_LOG_SELECTED, selectedTourLog);
    }
}
