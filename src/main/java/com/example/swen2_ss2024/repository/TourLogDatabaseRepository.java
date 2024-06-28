package com.example.swen2_ss2024.repository;

import com.example.swen2_ss2024.entity.TourLog;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import com.example.swen2_ss2024.entity.Tours;

import java.util.List;

public class TourLogDatabaseRepository implements TourLogRepository {

    private final EntityManagerFactory entityManagerFactory;

    public TourLogDatabaseRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("hibernate");
    }

    @Override
    public List<TourLog> findAll() {
        CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<TourLog> criteriaQuery = criteriaBuilder.createQuery(TourLog.class);
        Root<TourLog> root = criteriaQuery.from(TourLog.class);
        CriteriaQuery<TourLog> all = criteriaQuery.select(root);

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            List<TourLog> tourLogs = entityManager.createQuery(all).getResultList();
            return tourLogs;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public TourLog save(TourLog entity) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.persist(entity);
                transaction.commit();
                return entity;
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            }
        }
    }

    @Override
    public List<TourLog> findByTourId(Long tourId) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<TourLog> criteriaQuery = criteriaBuilder.createQuery(TourLog.class);
            Root<TourLog> root = criteriaQuery.from(TourLog.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("tour").get("id"), tourId));
            List<TourLog> tourLogs = entityManager.createQuery(criteriaQuery).getResultList();
            return tourLogs;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<TourLog> findByTourName(String tourName) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<TourLog> criteriaQuery = criteriaBuilder.createQuery(TourLog.class);
            Root<TourLog> root = criteriaQuery.from(TourLog.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("tour").get("name"), tourName));
            List<TourLog> tourLogs = entityManager.createQuery(criteriaQuery).getResultList();
            return tourLogs;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public TourLog findById(Long id) {

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TourLog tourLog = entityManager.find(TourLog.class, id);
            return tourLog;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteTourLog(TourLog tourLog) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Finden und löschen des TourLog
            TourLog attachedTourLog = entityManager.find(TourLog.class, tourLog.getId());
            if (attachedTourLog != null) {
                // Entfernen des TourLog aus der Tour-Entität
                Tours tour = attachedTourLog.getTour();
                if (tour != null) {
                    tour.getTourLogs().remove(attachedTourLog);
                    entityManager.merge(tour);  // Speichern der Änderungen an der Tour
                }
                entityManager.remove(attachedTourLog);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void modify(TourLog tourLog) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                TourLog existingTourLog = entityManager.find(TourLog.class, tourLog.getId());
                if (existingTourLog != null) {
                    existingTourLog.setName(tourLog.getName());
                    existingTourLog.setDate(tourLog.getDate());
                    existingTourLog.setRating(tourLog.getRating());
                    existingTourLog.setInfo(tourLog.getInfo());
                    entityManager.merge(existingTourLog);
                }
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            }
        }
    }

}