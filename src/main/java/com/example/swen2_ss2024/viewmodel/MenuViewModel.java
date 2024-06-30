package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.service.TourListService;
import com.example.swen2_ss2024.service.TourLogListService;

public class MenuViewModel {
    private final TourListService tourListService;
    private final TourLogListService tourLogListService;

    public MenuViewModel(TourListService tourListService, TourLogListService tourLogListService) {
        this.tourListService = tourListService;
        this.tourLogListService = tourLogListService;
    }

    public TourListService getTourListService() {
        return tourListService;
    }

    public TourLogListService getTourLogListService() {
        return tourLogListService;
    }

    public boolean isTourSelected() {
        return tourListService.getSelectedTour() != null;
    }

    public boolean isTourLogSelected() {
        return tourLogListService.getSelectedTourLog() != null;
    }
}
