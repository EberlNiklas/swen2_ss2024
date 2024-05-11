package com.example.swen2_ss2024;

import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.view.*;
import com.example.swen2_ss2024.viewmodel.*;
import com.example.swen2_ss2024.service.*;
public class ViewFactory {

    private static ViewFactory instance;

    private final Publisher publisher;

    private final SearchViewModel searchViewModel;

    private final MenuViewModel menuViewModel;
    private final TourViewModel tourViewModel;
    private final TabViewModel tabViewModel;

    private final TourLogsViewModel tourLogsViewModel;
    private final AddTourViewModel addTourViewModel;


    private final TourListService tourListService;
    private ViewFactory() {
        publisher = new Publisher();
        tourListService = new TourListService();
        searchViewModel = new SearchViewModel(publisher);
        menuViewModel = new MenuViewModel();
        tabViewModel = new TabViewModel();
        tourLogsViewModel = new TourLogsViewModel();
        tourViewModel = new TourViewModel(publisher, tourListService);
        addTourViewModel = new AddTourViewModel(publisher,tourListService);
    }

    public static ViewFactory getInstance() {
        if (null == instance) {
            instance = new ViewFactory();
        }

        return instance;
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
            TourLogsViewModel tourLogsViewModel = new TourLogsViewModel();
            return new TourLogsView(tourLogsViewModel);
        }

        if (TourView.class == viewClass) {
            return new TourView(tourViewModel);
        }

        if(AddTourView.class.equals(viewClass)) {
            return new AddTourView(addTourViewModel);
        }
        if (TourViewModel.class.equals(viewClass)) {
            return new TourView(tourViewModel);
        }


        throw new IllegalArgumentException("Unknown view class: " + viewClass);
    }
}
