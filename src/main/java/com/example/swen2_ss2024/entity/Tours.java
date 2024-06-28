package com.example.swen2_ss2024.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "t_tours")
public class Tours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;

    @Column(name = "from_destination")
    @JsonProperty("from")
    private String from;

    @Column(name = "to_destination")
    @JsonProperty("to")
    private String to;

    @Column(name = "transport_type")
    @JsonProperty("transport_type")
    private String transportType;

    @Column(name = "distance")
    @JsonProperty("distance")
    private String distance;

    @Column(name = "estimated_time")
    @JsonProperty("estimated_time")
    private String estimatedTime;

    @Column(name = "route_information")
    @JsonProperty("imagePath")
    private String imagePath;

    @Column(name = "tour_image")
    @Lob
    private byte[] tourImage;



    @OneToMany(
            targetEntity = TourLog.class,
            mappedBy = "tour",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)

    private List<TourLog> tourLogs = new ArrayList<>();


    // Default constructor
    public Tours() {
    }

    public Tours(String name, String description, String from, String to, String transportType, String distance, String estimatedTime, String imagePath) {
        this.name = name;
        this.description = description;
        this.from = from;
        this.to = to;
        this.transportType = transportType;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
        this.imagePath = imagePath;
    }
    public byte[] getTourImage() {
        return tourImage;
    }

    public void setTourImage(byte[] tourImage) {
        this.tourImage = tourImage;
    }

    // Getter and Setter methods
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<TourLog> getTourLogs() {
        return tourLogs;
    }

    public void setTourLogs(List<TourLog> tourLogs) {
        this.tourLogs = tourLogs;
    }

    public void addTourLog(TourLog tourLog) {
        tourLog.setTour(this);
        tourLogs.add(tourLog);
    }

    public void removeTourLog(TourLog tourLog) {
        tourLogs.remove(tourLog);
        tourLog.setTour(null);
    }
    public List<Object> getFieldsAsList() {
        return Arrays.asList(name, description, from, to, transportType, distance, estimatedTime, imagePath);
    }
}





