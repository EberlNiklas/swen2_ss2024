package com.example.swen2_ss2024.service;


import com.example.swen2_ss2024.entity.Tours;
import com.example.swen2_ss2024.repository.TourRepository;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TourListService {
    private Tours selectedTour;

    private boolean selected = false;

    private final TourRepository tourRepository;

    public TourListService(TourRepository tourRepository){
        this.tourRepository = tourRepository;
    }


    public void addTour(Tours tour) {
        tourRepository.save(tour);
    }

    public void editTour(Tours tour) {
        tourRepository.modify(tour);
    }

    public List<Tours> getTours() {
        return tourRepository.findAll();
    }

    public boolean deleteTourByName(String tourName) {
        if (tourRepository.findByName(tourName).isPresent()) {
            tourRepository.deleteByName(tourName);
            return true;
        }
        return false;
    }

    public void setSelected(Boolean selected){this.selected = selected; }

    public boolean selected() {
        return selected;
    }


    public void setSelectedTour(Tours selectedTour) {
        this.selectedTour = selectedTour;
    }

    public Tours getSelectedTour() {
        return selectedTour;
    }

    public void setImageURL(Tours selectedTour, URL url){
        tourRepository.saveTourURL(selectedTour, url);
    }

    public void saveTourImage(Tours tour, byte[] imageBytes) {
        tour.setTourImage(imageBytes);
        tourRepository.save(tour);
    }
    public void setDistance(Tours currentlySelected, double distance){
        tourRepository.saveTourDistance(currentlySelected, distance);
    }
    public void setDuration(Tours currentlySelected, double duration){
        tourRepository.saveTourDuration(currentlySelected, duration);
    }
    public Tours getTourByName(String name) {
        return tourRepository.findByName(name).orElse(null);
    }
}
