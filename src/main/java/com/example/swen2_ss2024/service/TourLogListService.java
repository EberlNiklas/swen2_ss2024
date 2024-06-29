package com.example.swen2_ss2024.service;

import com.example.swen2_ss2024.entity.TourLog;
import com.example.swen2_ss2024.repository.TourLogRepository;
import com.example.swen2_ss2024.repository.TourRepository;


import java.util.List;

public class TourLogListService {

    private final TourLogRepository tourLogRepository;
    private final TourRepository tourRepository;
    private boolean selected = false;
    private TourLog selectedTourLog;


    public TourLogListService(TourLogRepository tourLogRepository, TourRepository tourRepository) {
        this.tourLogRepository = tourLogRepository;
        this.tourRepository = tourRepository;
    }


    public void addTourLog(TourLog tourLog) {

        tourLogRepository.save(tourLog);
    }

    public boolean delete(Long tourLogId) {
        TourLog tourLog = tourLogRepository.findById(tourLogId);
        if (tourLog != null) {
            tourLogRepository.deleteTourLog(tourLog);
            return true;
        }
        return false;
    }

    public void editTourLog(TourLog newTourLog) {
        tourLogRepository.modify(newTourLog);
    }

    public void setSelected(Boolean selected){
        this.selected = selected;
    }

    public boolean selected() {return selected;}

    public List<TourLog> getTourLogsByTourName(String tourName) {
        return tourLogRepository.findByTourName(tourName);
    }

    public List<TourLog> getAllTourLogs() {
        return tourLogRepository.findAll();
    }

    public List<TourLog> getTourLogsByTourId(Long tourId) {
        return tourLogRepository.findByTourId(tourId);
    }

    public void setSelectedTourLog(TourLog selectedTourLog) {
        this.selectedTourLog = selectedTourLog;
    }

    public TourLog getSelectedTourLog() {
        return selectedTourLog;
    }

}
