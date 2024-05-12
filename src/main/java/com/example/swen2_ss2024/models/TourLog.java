package com.example.swen2_ss2024.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class TourLog {

    private final StringProperty name = new SimpleStringProperty(this, "name");
    private final StringProperty date = new SimpleStringProperty(this, "date");
    private final StringProperty duration = new SimpleStringProperty(this, "duration");
    private final StringProperty distance = new SimpleStringProperty(this, "distance");
    private final StringProperty comment = new SimpleStringProperty(this, "comment");
    private final StringProperty rating = new SimpleStringProperty(this, "rating");
    private final StringProperty difficulty = new SimpleStringProperty(this, "difficulty");

    public TourLog(String name, String date, String duration, String distance, String comment, String rating, String difficulty) {
        this.name.set(name);
        this.date.set(date);
        this.duration.set(duration);
        this.distance.set(distance);
        this.comment.set(comment);
        this.rating.set(rating);
        this.difficulty.set(difficulty);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getDuration() {
        return duration.get();
    }

    public StringProperty durationProperty() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration.set(duration);
    }

    public String getDistance() {
        return distance.get();
    }

    public StringProperty distanceProperty() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance.set(distance);
    }

    public String getComment() {
        return comment.get();
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public String getRating() {
        return rating.get();
    }

    public StringProperty ratingProperty() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating.set(rating);
    }

    public String getDifficulty() {
        return difficulty.get();
    }

    public StringProperty difficultyProperty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty.set(difficulty);
    }
}
