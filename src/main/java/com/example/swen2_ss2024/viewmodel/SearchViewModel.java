package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.entity.Tours;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.service.TourListService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
        Tours tour = tourListService.getTourByName(searchTerm);
        publisher.publish(Event.SEARCH_RESULT, tour);
        searchText.set("");
    }

    public void resetSearch() {
        System.out.println("Publishing RESET_SEARCH event"); // Debug line
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
