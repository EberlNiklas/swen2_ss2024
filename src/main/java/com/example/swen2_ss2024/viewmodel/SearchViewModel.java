package com.example.swen2_ss2024.viewmodel;


import com.example.swen2_ss2024.database.Database;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.models.Tour;
import com.example.swen2_ss2024.service.TourListService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.SQLException;

public class SearchViewModel {

    private final Publisher publisher;
    private final TourListService tourListService;
    private final StringProperty searchText = new SimpleStringProperty("");

    private final BooleanProperty disableSearch = new SimpleBooleanProperty(true);

    public SearchViewModel(Publisher publisher, TourListService tourListService) {
        this.publisher = publisher;
        this.tourListService = tourListService;
        this.searchText.addListener(observable -> disableSearch.set(searchText.get().isEmpty()));
    }

    public void search() {
        if (disableSearch.get()) {
            return;
        }

        String searchTerm = searchText.get();
        try {
            Tour tour = Database.getTourByName(searchTerm);
            if (tour != null) {
                publisher.publish(Event.SEARCH_RESULT, tour);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        searchText.set("");
    }

    public void resetSearch() {
        publisher.publish(Event.RESET_SEARCH, new Object());
    }

    public StringProperty searchTextProperty() {
        return searchText;
    }

    public String getSearchText() {
        return searchText.get();
    }

    public BooleanProperty searchDisabledProperty() {
        return disableSearch;
    }

    public boolean getSearchDisabled() {
        return disableSearch.get();
    }

    public boolean isSearchDisabled() {
        return disableSearch.get();
    }
}
