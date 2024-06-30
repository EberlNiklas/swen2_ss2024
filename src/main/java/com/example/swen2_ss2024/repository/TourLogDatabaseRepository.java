package com.example.swen2_ss2024.repository;

import com.example.swen2_ss2024.entity.TourLog;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import com.example.swen2_ss2024.entity.Tours;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class TourLogDatabaseRepository implements TourLogRepository {

    private static final Logger logger = LogManager.getLogger(TourLogDatabaseRepository.class);

    private final EntityManagerFactory entityManagerFactory;

    public TourLogDatabaseRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("hibernate");
    }

    @Override
    public List<TourLog> findAll() {
        logger.debug("Attempt to find all tour logs");
        CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<TourLog> criteriaQuery = criteriaBuilder.createQuery(TourLog.class);
        Root<TourLog> root = criteriaQuery.from(TourLog.class);
        CriteriaQuery<TourLog> all = criteriaQuery.select(root);

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            List<TourLog> tourLogs = entityManager.createQuery(all).getResultList();
            logger.info("There are {} tour logs", tourLogs.size());
            return tourLogs;
        } catch (Exception e) {
            logger.error("Error finding all tour logs", e);
            throw e;
        }
    }

    @Override
    public TourLog save(TourLog entity) {
        logger.debug("Attempt to create tour log: {}", entity);
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
            logger.info("Created tour log: {}", entity);
        } catch (Exception e){
            logger.error("Error creating tour log: {}", entity);
            throw e;
        }
        return entity;
    }

    @Override
    public List<TourLog> findByTourId(Long tourId) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<TourLog> criteriaQuery = criteriaBuilder.createQuery(TourLog.class);
            Root<TourLog> root = criteriaQuery.from(TourLog.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("tour").get("id"), tourId));
            List<TourLog> tourLogs = entityManager.createQuery(criteriaQuery).getResultList();
            logger.info("Found {} tour logs for tour id: {}", tourLogs.size(), tourId);
            return tourLogs;
        } catch (Exception e) {
            logger.error("Error finding tour logs by tour id: {}", tourId, e);
            throw e;
        }
    }

    @Override
    public List<TourLog> findByTourName(String tourName) {
        logger.debug("Attempt to find tour logs by tour name: {}", tourName);
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<TourLog> criteriaQuery = criteriaBuilder.createQuery(TourLog.class);
            Root<TourLog> root = criteriaQuery.from(TourLog.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("tour").get("name"), tourName));
            List<TourLog> tourLogs = entityManager.createQuery(criteriaQuery).getResultList();
            logger.info("Found {} tour logs for tour name: {}", tourLogs.size(), tourName);
            return tourLogs;
        } catch (Exception e) {
            logger.error("Error finding tour logs by tour name: {}", tourName, e);
            throw e;
        }
    }

    @Override
    public TourLog findById(Long id) {
        logger.debug("Attempt to find tour log by id: {}", id);
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TourLog tourLog = entityManager.find(TourLog.class, id);
            return tourLog;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteTourLog(TourLog tourLog) {
        logger.debug("Attempt to delete tour log: {}", tourLog);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            //Find and delete Tour log
            TourLog attachedTourLog = entityManager.find(TourLog.class, tourLog.getId());
            if (attachedTourLog != null) {
                //Deleting Tour log from Entity
                Tours tour = attachedTourLog.getTour();
                if (tour != null) {
                    tour.getTourLogs().remove(attachedTourLog);
                    entityManager.merge(tour);  // Saving changes
                }
                entityManager.remove(attachedTourLog);
                logger.info("Deleted tour log: {}", tourLog);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.warn("No tour log found to delete: {}", tourLog);
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void modify(TourLog tourLog) {
        logger.debug("Attempt to modify tour log: {}", tourLog);
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
                    transaction.commit();
                } else {
                    logger.warn("No tour log found to modify: {}", tourLog);
                }

            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                logger.error("Error modifying tour log: {}", tourLog, e);
                throw e;
            }
        }
    }

}
