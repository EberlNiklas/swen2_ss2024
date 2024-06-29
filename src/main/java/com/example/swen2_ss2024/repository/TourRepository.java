package com.example.swen2_ss2024.repository;

import com.example.swen2_ss2024.entity.Tours;

import java.net.URL;
import java.util.List;
import java.util.Optional;

public interface TourRepository {

    List<Tours> findAll();
    Tours save(Tours entity);
    Optional<Tours> findByName(String name);
    void deleteByName(String name);
    Optional<Tours> findById(Long id);
    void modify(Tours tour);
    void saveTourURL(Tours entity, URL url);
    void saveTourDistance(Tours entity, double distance);
    void saveTourDuration(Tours entity, double duration);
}
