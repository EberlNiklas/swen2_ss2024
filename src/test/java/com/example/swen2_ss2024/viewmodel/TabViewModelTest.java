package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.entity.Tours;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.Publisher;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TabViewModelTest {

    private TabViewModel viewModel;
    private Publisher publisher;

    @BeforeEach
    public void setUp() {
        publisher = mock(Publisher.class);
        viewModel = new TabViewModel(publisher);
    }

    @Test
    public void testInitialState() {
        assertTrue(viewModel.getTourDetails().isEmpty(), "Tour details should be empty initially.");
        assertNull(viewModel.routeImageProperty().get(), "Route image should be null initially.");
    }

    @Test
    public void testNotifyTourDeleted() {
        // Arrange
        when(publisher.getCurrentEvent()).thenReturn(Event.TOUR_DELETED);

        // Act
        viewModel.notify(new Tours());

        // Assert
        assertTrue(viewModel.getTourDetails().isEmpty(), "Tour details should be empty after tour is deleted.");
        assertNull(viewModel.routeImageProperty().get(), "Route image should be null after tour is deleted.");
    }

    @Test
    public void testNotifyResetSearch() {
        // Arrange
        when(publisher.getCurrentEvent()).thenReturn(Event.RESET_SEARCH);

        // Act
        viewModel.notify(new Object());

        // Assert
        assertTrue(viewModel.getTourDetails().isEmpty(), "Tour details should be empty after reset search.");
        assertNull(viewModel.routeImageProperty().get(), "Route image should be null after reset search.");
    }
}
