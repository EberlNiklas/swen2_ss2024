package com.example.swen2_ss2024.viewmodel;


import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.Publisher;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SearchViewModel {

    private final Publisher publisher;

    private final StringProperty searchText = new SimpleStringProperty("");

    private final BooleanProperty disableSearch = new SimpleBooleanProperty(true);

    public SearchViewModel(Publisher publisher){
        this.publisher = publisher;

        this.searchText.addListener(observable -> disableSearch.set(searchText.get().isEmpty()));
    }

    public void search() {
        if (disableSearch.get()) {
            return;
        }

        publisher.publish(Event.SEARCH_TERM_SEARCHED, searchText.get());

        searchText.set("");
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
