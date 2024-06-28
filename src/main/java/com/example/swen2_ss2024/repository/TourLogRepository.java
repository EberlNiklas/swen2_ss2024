package com.example.swen2_ss2024.repository;

import com.example.swen2_ss2024.entity.TourLog;

import java.util.List;

public interface TourLogRepository {

    List<TourLog> findAll();
    TourLog save(TourLog entity);
    List<TourLog> findByTourId(Long tourId);
    List<TourLog> findByTourName(String tourName);
    TourLog findById(Long id);
    void deleteTourLog(TourLog tourLog);
    void modify(TourLog newTourLog);
}
