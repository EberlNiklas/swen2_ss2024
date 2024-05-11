package com.example.swen2_ss2024.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tour {
    private final StringProperty name = new SimpleStringProperty(this, "name");
    private final StringProperty description = new SimpleStringProperty(this, "description");
    private final StringProperty from = new SimpleStringProperty(this, "from");
    private final StringProperty to = new SimpleStringProperty(this, "to");
    private final StringProperty transportType = new SimpleStringProperty(this, "transportType");
    private final StringProperty distance = new SimpleStringProperty(this, "distance");
    private final StringProperty estimatedTime = new SimpleStringProperty(this, "estimatedTime");
    private final StringProperty imagePath = new SimpleStringProperty(this, "imagePath");

    public Tour(String name, String description, String from, String to, String transportType, String distance, String estimatedTime, String imagePath) {
        this.name.set(name);
        this.description.set(description);
        this.from.set(from);
        this.to.set(to);
        this.transportType.set(transportType);
        this.distance.set(distance);
        this.estimatedTime.set(estimatedTime);
        this.imagePath.set(imagePath);
    }

    // Property getters
    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty fromProperty() {
        return from;
    }

    public StringProperty toProperty() {
        return to;
    }

    public StringProperty transportTypeProperty() {
        return transportType;
    }

    public StringProperty distanceProperty() {
        return distance;
    }

    public StringProperty estimatedTimeProperty() {
        return estimatedTime;
    }

    // Normal getters
    public String getName() {
        return name.get();
    }

    public String getDescription() {
        return description.get();
    }

    public String getFrom() {
        return from.get();
    }

    public String getTo() {
        return to.get();
    }

    public String getTransportType() {
        return transportType.get();
    }

    public String getDistance() {
        return distance.get();
    }

    public String getEstimatedTime() {
        return estimatedTime.get();
    }

    // Setters
    public void setName(String name) {
        this.name.set(name);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public void setFrom(String from) {
        this.from.set(from);
    }

    public void setTo(String to) {
        this.to.set(to);
    }

    public void setTransportType(String transportType) {
        this.transportType.set(transportType);
    }

    public void setDistance(String distance) {
        this.distance.set(distance);
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime.set(estimatedTime);
    }
    public String getImagePath() {
        return imagePath.get();
    }

    public void setImagePath(String imagePath) {
        this.imagePath.set(imagePath);
    }

    public StringProperty imagePathProperty() {
        return imagePath;
    }
}
