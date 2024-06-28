package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.entity.Tours;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.service.TourListService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SearchViewModelTest {

    private SearchViewModel viewModel;
    private Publisher publisher;
    private TourListService tourListService;

    @BeforeEach
    public void setUp() {
        publisher = mock(Publisher.class);
        tourListService = mock(TourListService.class);
        viewModel = new SearchViewModel(publisher, tourListService);
    }

    @Test
    public void testInitialState() {
        assertNotNull(viewModel.searchTextProperty());
        assertTrue(viewModel.getSearchDisabled(), "Search should be disabled initially.");
    }

    @Test
    public void testSearchWithDisabledState() {
        // Arrange
        viewModel.searchTextProperty().set("");

        // Act
        viewModel.search();

        // Assert
        verify(publisher, never()).publish(any(Event.class), any());
    }

    @Test
    public void testSearchWithEnabledState() {
        // Arrange
        String searchTerm = "Test Tour";
        Tours mockTour = new Tours();
        when(tourListService.getTourByName(searchTerm)).thenReturn(mockTour);
        viewModel.searchTextProperty().set(searchTerm);

        // Act
        viewModel.search();

        // Assert
        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        ArgumentCaptor<Object> objectCaptor = ArgumentCaptor.forClass(Object.class);
        verify(publisher).publish(eventCaptor.capture(), objectCaptor.capture());
        assertEquals(Event.SEARCH_RESULT, eventCaptor.getValue());
        assertEquals(mockTour, objectCaptor.getValue());
        assertTrue(viewModel.getSearchText().isEmpty(), "Search text should be cleared after search.");
    }

    @Test
    public void testResetSearch() {
        // Act
        viewModel.resetSearch();

        // Assert
        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        ArgumentCaptor<Object> objectCaptor = ArgumentCaptor.forClass(Object.class);
        verify(publisher).publish(eventCaptor.capture(), objectCaptor.capture());
        assertEquals(Event.RESET_SEARCH, eventCaptor.getValue());
    }

    @Test
    public void testSearchTextPropertyBinding() {
        // Arrange
        StringProperty testProperty = new SimpleStringProperty("Test");
        viewModel.searchTextProperty().bindBidirectional(testProperty);

        // Act
        testProperty.set("New Test");

        // Assert
        assertEquals("New Test", viewModel.getSearchText());
    }

    @Test
    public void testSearchDisabledPropertyBinding() {
        // Act
        viewModel.searchTextProperty().set("New Search Text");

        // Assert
        assertFalse(viewModel.getSearchDisabled(), "Search should be enabled when search text is not empty.");
    }
}


