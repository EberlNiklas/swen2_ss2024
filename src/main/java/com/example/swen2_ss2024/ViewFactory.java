package com.example.swen2_ss2024;

import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.repository.TourDatabaseRepository;
import com.example.swen2_ss2024.repository.TourLogDatabaseRepository;
import com.example.swen2_ss2024.repository.TourLogRepository;
import com.example.swen2_ss2024.repository.TourRepository;
import com.example.swen2_ss2024.service.TourListService;
import com.example.swen2_ss2024.service.TourLogListService;
import com.example.swen2_ss2024.view.*;
import com.example.swen2_ss2024.viewmodel.*;

public class ViewFactory {

    private static ViewFactory instance;

    private final Publisher publisher;

    private final SearchViewModel searchViewModel;
    private final MenuViewModel menuViewModel;
    private final TourViewModel tourViewModel;
    private final TabViewModel tabViewModel;
    private final TourLogsViewModel tourLogsViewModel;
    private final AddTourViewModel addTourViewModel;
    private final AddTourLogViewModel addTourLogViewModel;

    private final TourListService tourListService;
    private final TourLogListService tourLogListService;

    private final EditTourViewModel editTourViewModel;

    private final TourRepository tourRepository;

    private final TourLogRepository tourLogRepository;

    private final EditTourLogViewModel editTourLogViewModel;


    private ViewFactory() {
        publisher = new Publisher();
        tourLogRepository = new TourLogDatabaseRepository();
        tourRepository = new TourDatabaseRepository();
        tourListService = new TourListService(tourRepository);
        tourLogListService = new TourLogListService(tourLogRepository, tourRepository);
        searchViewModel = new SearchViewModel(publisher, tourListService); // Pass tourListService here
        menuViewModel = new MenuViewModel();
        tourViewModel = new TourViewModel(publisher, tourListService, tourLogListService);
        tabViewModel = new TabViewModel(publisher);
        tourLogsViewModel = new TourLogsViewModel(publisher, tourLogListService, tourListService);
        addTourViewModel = new AddTourViewModel(publisher, tourListService);
        addTourLogViewModel = new AddTourLogViewModel(publisher, tourLogListService, tourListService);
        editTourViewModel = new EditTourViewModel(publisher, tourListService);
        editTourLogViewModel = new EditTourLogViewModel(publisher, tourLogListService, tourListService);
    }


    public static ViewFactory getInstance() {
        if (instance == null) {
            instance = new ViewFactory();
        }
        return instance;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public TourListService getTourListService() {
        return tourListService;
    }

    public Object create(Class<?> viewClass) {
        if (SearchBarView.class == viewClass) {
            return new SearchBarView(searchViewModel);
        }
        if (MenuView.class == viewClass) {
            return new MenuView(menuViewModel);
        }
        if (TabView.class == viewClass) {
            return new TabView(tabViewModel);
        }
        if (TourLogsView.class == viewClass) {
            return new TourLogsView(tourLogsViewModel);
        }
        if (TourView.class == viewClass) {
            return new TourView(tourViewModel);
        }
        if (AddTourView.class == viewClass) {
            return new AddTourView(addTourViewModel);
        }
        if (EditTourView.class == viewClass) {
            return new EditTourView(editTourViewModel); // Use no-argument constructor
        }
        if (AddTourLogView.class == viewClass) {
            return new AddTourLogView(addTourLogViewModel);
        }
        if (EditTourLogView.class == viewClass) {
            return new EditTourLogView(editTourLogViewModel);
        }

        throw new IllegalArgumentException("Unknown view class: " + viewClass);
    }
}
