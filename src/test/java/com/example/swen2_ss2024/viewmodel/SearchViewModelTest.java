/*package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.service.TourListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class SearchViewModelTest {

    private SearchViewModel viewModel;

    private Publisher publisher;

    @BeforeEach
    public void beforeEach() {
        publisher = new Publisher();
        TourListService tourListService = new TourListService();
        SearchViewModel searchViewModel = new SearchViewModel(publisher, tourListService);
    }

    @Test
    public void should_disableSearch_when_noSearchTerm() {
        viewModel.searchTextProperty().set("");

        assertTrue(viewModel.isSearchDisabled());
    }

    @Test
    public void should_enableSearch_when_searchTerm() {
        viewModel.searchTextProperty().set("Hello World");

        assertFalse(viewModel.isSearchDisabled());
    }
}*/