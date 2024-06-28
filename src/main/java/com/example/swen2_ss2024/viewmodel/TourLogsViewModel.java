package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.ViewFactory;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.ObjectSubscriber;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.entity.TourLog;
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

import java.util.List;

public class TourLogsViewModel implements ObjectSubscriber {

    private final ObservableList<TourLog> tourLogList = FXCollections.observableArrayList();
    private final IntegerProperty index = new SimpleIntegerProperty();
    private TourLogListService tourLogListService;
    private Publisher publisher;

    public TourLogsViewModel(Publisher publisher, TourLogListService tourLogListService) {
        this.publisher = publisher;
        this.tourLogListService = tourLogListService;

        // Subscribe this ViewModel to the TOUR_LOG_ADDED event
        this.publisher.subscribe(Event.SELECTED_TOUR_CHANGED, (ObjectSubscriber) this::updateTourLogs);
        this.publisher.subscribe(Event.TOUR_LOG_ADDED, (ObjectSubscriber) this::addToTourLogs);

        loadTourLogs();

        // Add listener to handle selection index changes
        this.index.addListener((obs, oldVal, newVal) -> {
            selectTourLogIndex(newVal.intValue());
        });
    }

    public void loadTourLogs() {
        List<TourLog> logs = tourLogListService.getAllTourLogs();
        tourLogList.clear();
        tourLogList.addAll(logs);
    }

    public void addToTourLogs(Object message) {
        if (message instanceof TourLog) {
            TourLog tourLog = (TourLog) message;
            tourLogList.add(tourLog);
        }
    }

    public void delete() {
        int tourLogIndex = index.get();
        if (tourLogIndex >= 0 && tourLogIndex < tourLogList.size()) {
            TourLog tourLog = tourLogList.get(tourLogIndex);
            if (tourLogListService.deleteTourById(tourLog.getId())) {
                tourLogList.remove(tourLogIndex);
            }
        }
    }


    public void selectTourLogIndex(int index) {
        if (index >= 0 && index < tourLogList.size()) {
            TourLog tourLog = tourLogList.get(index);
            tourLogListService.setSelectedTourLog(tourLog);
            tourLogListService.setSelected(true);
        }
    }


    public void updateTourLogs(Object message) {
        if (message instanceof Long) {
            Long selectedTourId = (Long) message;
            List<TourLog> logs = tourLogListService.getTourLogsByTourId(selectedTourId);
            tourLogList.clear();
            tourLogList.addAll(logs);
        }
    }

    public ObservableList<TourLog> getTourLogs() {
        return tourLogList;
    }

    public IntegerProperty selectedAddTourProperty() {
        return index;
    }

    @Override
    public void notify(Object message) {
        // Implementation for the notify method if needed
    }
}
