package com.example.swen2_ss2024.models;

public class Tour {
    private String name;
    private String description;
    /*private String from;
    private String to;
    private String transportType;
    private double distance;  // in kilometers
    private double estimatedTime; // in hours
    private String routeInformation; // URL to the image map*/

    public Tour(String name, String description /*,String from, String to, String transportType, double distance, double estimatedTime, String routeInformation*/) {
        this.name = name;
        this.description = description;
        /*this.from = from;
        this.to = to;
        this.transportType = transportType;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
        this.routeInformation = routeInformation;*/
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    /*public String getFrom() { return from; }
    public String getTo() { return to; }
    public String getTransportType() { return transportType; }
    public double getDistance() { return distance; }
    public double getEstimatedTime() { return estimatedTime; }
    public String getRouteInformation() { return routeInformation; }*/

    // Setters
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    /*public void setFrom(String from) { this.from = from; }
    public void setTo(String to) { this.to = to; }
    public void setTransportType(String transportType) { this.transportType = transportType; }
    public void setDistance(double distance) { this.distance = distance; }
    public void setEstimatedTime(double estimatedTime) { this.estimatedTime = estimatedTime; }
    public void setRouteInformation(String routeInformation) { this.routeInformation = routeInformation; }*/
    @Override
    public String toString() {
        return name + " - " + description; // Or any other format you prefer
    }
}
