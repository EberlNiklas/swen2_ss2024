package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.event.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class SearchViewModelTest {

    private SearchViewModel viewModel;

    private Publisher publisher;

    @BeforeEach
    public void beforeEach() {
        publisher = new Publisher();
        viewModel = new SearchViewModel(publisher);
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
}