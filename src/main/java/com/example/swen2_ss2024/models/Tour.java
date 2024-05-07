package com.example.swen2_ss2024.models;

public class Tour {
    private String id;
    private String name;
    private String description;
    /*private String from;
    private String to;
    private String transportType;
    private double tourDistance;  // in kilometers
    private double estimatedTime; // in hours
    private String routeInformation; // URL to the image map*/

    public Tour(String id, String name, String description/*, String from, String to, String transportType*/) {
        this.id = id;
        this.name = name;
        this.description = description;
        /*this.from = from;
        this.to = to;
        this.transportType = transportType;
        this.tourDistance = 0;
        this.estimatedTime = 0;
        this.routeInformation = "";*/
    }

    // Getters and setters for all fields
    @Override
    public String toString() {
        return id + " - " + name + " - " + description; // For displaying the placeholder, I do not know what is needed later on
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
/*
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

    public double getTourDistance() {
        return tourDistance;
    }

    public void setTourDistance(double tourDistance) {
        this.tourDistance = tourDistance;
    }

    public double getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(double estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getRouteInformation() {
        return routeInformation;
    }

    public void setRouteInformation(String routeInformation) {
        this.routeInformation = routeInformation;
    }
    */

}
