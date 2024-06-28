package com.example.swen2_ss2024.repository;

import com.example.swen2_ss2024.entity.TourLog;
import com.example.swen2_ss2024.entity.Tours;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import java.net.URL;

import java.util.List;
import java.util.Optional;

public class TourDatabaseRepository implements TourRepository {


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
                return entityManager.createQuery(all).getResultList();
            } catch (Exception e) {
                throw e;
            }
        }

        @Override
        public Tours save(Tours entity) {
            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                EntityTransaction transaction = entityManager.getTransaction();
                transaction.begin();
                entityManager.persist(entity);
                transaction.commit();
            } catch (Exception e) {
                throw e;
            }
            return entity;
        }

        @Override
        public Optional<Tours> findByName(String name) {
            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                CriteriaQuery<Tours> criteriaQuery = criteriaBuilder.createQuery(Tours.class);
                Root<Tours> root = criteriaQuery.from(Tours.class);
                criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("name"), name));
                List<Tours> results = entityManager.createQuery(criteriaQuery).getResultList();
                return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
            } catch (Exception e) {
                throw e;
            }
        }

        @Override
        public Integer getTourIndexByName(String name) {
            return null;
        }

        @Override
        public Optional<Tours> findById(Long id) {
            if (id == null) {
                return Optional.empty();
            }
            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                Tours tour = entityManager.find(Tours.class, id);
                return Optional.ofNullable(tour);
            } catch (Exception e) {
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
                    }
                } catch (Exception e) {
                    if (transaction.isActive()) {
                        transaction.rollback();
                    }
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
            }
        }
        @Override
        public void saveTourDistance(Tours entity, double distance) {
            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                EntityTransaction transaction = entityManager.getTransaction();
                transaction.begin();
                entity.setDistance(String.valueOf(distance));
                entityManager.merge(entity);
                transaction.commit();
            }
        }
        @Override
        public void saveTourDuration(Tours entity, double duration) {
            try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
                EntityTransaction transaction = entityManager.getTransaction();
                transaction.begin();
                entity.setEstimatedTime(String.valueOf(duration));
                entityManager.merge(entity);
                transaction.commit();
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
                    entityManager.remove(log);
                }

                // Löschen der Tour
                CriteriaQuery<Tours> tourQuery = criteriaBuilder.createQuery(Tours.class);
                Root<Tours> tourRoot = tourQuery.from(Tours.class);
                tourQuery.select(tourRoot).where(criteriaBuilder.equal(tourRoot.get("name"), name));
                List<Tours> tours = entityManager.createQuery(tourQuery).getResultList();
                if (!tours.isEmpty()) {
                    entityManager.remove(tours.get(0));
                }

                transaction.commit();
            } catch (Exception e) {
                throw e;
            }
        }
}
