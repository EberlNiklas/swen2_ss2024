package com.example.swen2_ss2024;

import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.view.*;
import com.example.swen2_ss2024.viewmodel.*;

public class ViewFactory {

    private static ViewFactory instance;

    private final Publisher publisher;

    private final SearchViewModel searchViewModel;

    private final MenuViewModel menuViewModel;

    private final TabViewModel tabViewModel;

    private final TourLogsViewModel tourLogsViewModel;

    private final TourViewModel tourViewModel;

    private ViewFactory() {
        publisher = new Publisher();

        searchViewModel = new SearchViewModel(publisher);
        menuViewModel = new MenuViewModel();
        tabViewModel = new TabViewModel();
        tourLogsViewModel = new TourLogsViewModel();
        tourViewModel = new TourViewModel();
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
            return new TourLogsView(tourLogsViewModel);
        }

        if (TourView.class == viewClass) {
            return new TourView(tourViewModel);
        }
        if (viewClass == TourPlannerController.class) {
            return new TourPlannerController();
        }


        throw new IllegalArgumentException("Unknown view class: " + viewClass);
    }


}
