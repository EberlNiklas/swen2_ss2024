package com.example.swen2_ss2024;

import com.example.swen2_ss2024.event.Publisher;
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

    private ViewFactory() {
        publisher = new Publisher();
        tourListService = new TourListService();
        tourLogListService = new TourLogListService();
        searchViewModel = new SearchViewModel(publisher, tourListService); // Pass tourListService here
        menuViewModel = new MenuViewModel();
        tourViewModel = new TourViewModel(publisher, tourListService);
        tabViewModel = new TabViewModel(publisher);
        tourLogsViewModel = new TourLogsViewModel(publisher, tourLogListService);
        addTourViewModel = new AddTourViewModel(publisher, tourListService);
        addTourLogViewModel = new AddTourLogViewModel(publisher, tourLogListService);
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
            return new EditTourView(); // Use no-argument constructor
        }
        if (AddTourLogView.class == viewClass) {
            return new AddTourLogView(addTourLogViewModel);
        }
        if (EditTourLogView.class == viewClass) {
            return new EditTourLogView(EditTourLogViewModel.getInstance(publisher, tourLogListService));
        }

        throw new IllegalArgumentException("Unknown view class: " + viewClass);
    }
}
