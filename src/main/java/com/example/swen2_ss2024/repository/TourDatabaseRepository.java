package com.example.swen2_ss2024.repository;

import com.example.swen2_ss2024.entity.TourLog;
import com.example.swen2_ss2024.entity.Tours;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import java.net.URL;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TourDatabaseRepository implements TourRepository {

    private static final Logger logger = LogManager.getLogger(TourDatabaseRepository.class);

    private final EntityManagerFactory entityManagerFactory;

    public TourDatabaseRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("hibernate");
    }

    @Override
    public List<Tours> findAll() {
        CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Tours> criteriaQuery = criteriaBuilder.createQuery(Tours.class);
        Root<Tours> root = criteriaQuery.from(Tours.class);
        CriteriaQuery<Tours> all = criteriaQuery.select(root);

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            logger.info("Finding all tours");
            return entityManager.createQuery(all).getResultList();
        } catch (Exception e) {
            logger.error("Error finding all tours", e);
            throw e;
        }
    }

    @Override
    public Tours save(Tours entity) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            if (isTableEmpty()) {
                resetAutoIncrement();
            }
            entityManager.persist(entity);
            transaction.commit();
            logger.info("Tour created: {}", entity.getName());
        } catch (Exception e) {
            logger.error("Error creating tour: {}", entity.getName(), e);
            throw e;
        }
        return entity;
    }

    private boolean isTableEmpty() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<Tours> root = criteriaQuery.from(Tours.class);
            criteriaQuery.select(criteriaBuilder.count(root));
            Long count = entityManager.createQuery(criteriaQuery).getSingleResult();
            logger.debug("Table empty check, count: {}", count);
            return count == 0;
        }
        catch (Exception e) {
            logger.error("Error checking if table is empty", e);
            throw e;
        }
    }

    private void resetAutoIncrement() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.createNativeQuery("ALTER SEQUENCE t_tours_id_seq RESTART WITH 1").executeUpdate();
            transaction.commit();
            logger.info("Auto increment reset");
        }
        catch (Exception e) {
            logger.error("Error resetting auto increment", e);
            throw e;
        }
    }

    @Override
    public Optional<Tours> findByName(String name) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Tours> criteriaQuery = criteriaBuilder.createQuery(Tours.class);
            Root<Tours> root = criteriaQuery.from(Tours.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("name"), name));
            List<Tours> results = entityManager.createQuery(criteriaQuery).getResultList();
            logger.info("Search for tour by name was successful: {}", name);
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } catch (Exception e) {
            logger.error("Error while searching for tour by name: {}", name, e);
            throw e;
        }
    }

    @Override
    public Optional<Tours> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Tours tour = entityManager.find(Tours.class, id);
            logger.info("Search for tour by id was successful: {}", id);
            return Optional.ofNullable(tour);
        } catch (Exception e) {
            logger.error("Error while searching for tour by id: {}", id, e);
            throw e;
        }
    }

    @Override
    public void modify(Tours tour) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                Tours existingTour = entityManager.find(Tours.class, tour.getId());
                if (existingTour != null) {
                    existingTour.setName(tour.getName());
                    existingTour.setDescription(tour.getDescription());
                    existingTour.setFrom(tour.getFrom());
                    existingTour.setTo(tour.getTo());
                    existingTour.setTransportType(tour.getTransportType());
                    existingTour.setDistance(tour.getDistance());
                    existingTour.setEstimatedTime(tour.getEstimatedTime());
                    existingTour.setImagePath(tour.getImagePath());
                    entityManager.merge(existingTour);
                    transaction.commit();
                    logger.info("Edited tour: {}", tour);
                }else {
                    logger.warn("No tour found: {}", tour.getId());
                }
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                logger.error("Error editing tour: {}", tour.getId(), e);
                throw e;
            }
        }
    }

    @Override
    public void saveTourURL(Tours entity, URL url) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entity.setImagePath(url.toString());
            entityManager.merge(entity);
            transaction.commit();
            logger.info("Saved tour URL for tour: {}", entity.getName());
        }
        catch (Exception e) {
            logger.error("Error saving tour URL for tour: {}", entity.getName(), e);
            throw e;
        }
    }
    @Override
    public void saveTourDistance(Tours entity, double distance) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entity.setDistance(distance);
            entityManager.merge(entity);
            transaction.commit();
            logger.info("Saved tour distance for tour: {}", entity.getName());
        }
        catch (Exception e) {
            logger.error("Error saving tour distance for tour: {}", entity.getName(), e);
            throw e;
        }
    }
    @Override
    public void saveTourDuration(Tours entity, double duration) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entity.setEstimatedTime(duration);
            entityManager.merge(entity);
            transaction.commit();
            logger.info("Saved tour duration for tour: {}", entity.getName());
        }
        catch (Exception e) {
            logger.error("Error saving tour duration for tour: {}", entity.getName(), e);
            throw e;
        }
    }


    @Override
    public void deleteByName(String name) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            // Löschen der zugehörigen TourLogs
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<TourLog> logQuery = criteriaBuilder.createQuery(TourLog.class);
            Root<TourLog> logRoot = logQuery.from(TourLog.class);
            logQuery.select(logRoot).where(criteriaBuilder.equal(logRoot.get("tour").get("name"), name));
            List<TourLog> tourLogs = entityManager.createQuery(logQuery).getResultList();
            for (TourLog log : tourLogs) {
                logger.debug("Attempt to delete tour logs for tour: {}", name);
                entityManager.remove(log);
                logger.info("Successfully deleted tour log for: {}", name);
            }

            // Löschen der Tour
            CriteriaQuery<Tours> tourQuery = criteriaBuilder.createQuery(Tours.class);
            Root<Tours> tourRoot = tourQuery.from(Tours.class);
            tourQuery.select(tourRoot).where(criteriaBuilder.equal(tourRoot.get("name"), name));
            List<Tours> tours = entityManager.createQuery(tourQuery).getResultList();
            if (!tours.isEmpty()) {
                logger.debug("Attempt to delete tour: {}", name);
                entityManager.remove(tours.get(0));
                logger.info("Successfully deleted tour: {}", name);
            }

            transaction.commit();
        } catch (Exception e) {
            logger.error("Error deleting tour: {}", name, e);
            throw e;
        }
    }
}
