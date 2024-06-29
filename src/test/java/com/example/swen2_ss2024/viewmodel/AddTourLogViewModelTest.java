package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.entity.TourLog;
import com.example.swen2_ss2024.entity.Tours;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.service.TourListService;
import com.example.swen2_ss2024.service.TourLogListService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddTourLogViewModelTest {

    private AddTourLogViewModel viewModel;
    private Publisher publisher;
    private TourLogListService tourLogListService;
    private TourListService tourListService;

    @BeforeEach
    public void beforeEach() {
        publisher = mock(Publisher.class);
        tourLogListService = mock(TourLogListService.class);
        tourListService = mock(TourListService.class);
        viewModel = new AddTourLogViewModel(publisher, tourLogListService, tourListService);
    }

    @Test
    public void testInitialState() {
        assertTrue(viewModel.addTourLogButtonDisabledProperty().get(), "Add Tour Log button should be disabled initially.");
    }

    @Test
    public void testAddTourLogButtonDisabledWhenFieldsAreEmpty() {
        viewModel.nameProperty().set("");
        viewModel.dateProperty().set("");
        viewModel.ratingProperty().set("");
        viewModel.infoProperty().set("");
        assertTrue(viewModel.addTourLogButtonDisabledProperty().get(), "Add Tour Log button should be disabled when fields are empty.");
    }

    @Test
    public void testAddTourLogButtonEnabledWhenFieldsAreNotEmpty() {
        viewModel.nameProperty().set("Log Name");
        viewModel.dateProperty().set("2024-06-30");
        viewModel.ratingProperty().set("5");
        viewModel.infoProperty().set("Info about the tour log");
        assertFalse(viewModel.addTourLogButtonDisabledProperty().get(), "Add Tour Log button should be enabled when all fields are filled.");
    }

    @Test
    public void testAddTourLog() {
        Tours mockTour = new Tours();
        when(tourListService.selected()).thenReturn(true);
        when(tourListService.getSelectedTour()).thenReturn(mockTour);

        viewModel.nameProperty().set("Log Name");
        viewModel.dateProperty().set("2024-06-30");
        viewModel.ratingProperty().set("5");
        viewModel.infoProperty().set("Info about the tour log");

        viewModel.addTourLog();

        ArgumentCaptor<TourLog> tourLogCaptor = ArgumentCaptor.forClass(TourLog.class);
        verify(tourLogListService).addTourLog(tourLogCaptor.capture());
        verify(publisher).publish(eq(Event.TOUR_LOG_ADDED), tourLogCaptor.capture());

        TourLog capturedTourLog = tourLogCaptor.getValue();
        assertEquals("Log Name", capturedTourLog.getName());
        assertEquals("2024-06-30", capturedTourLog.getDate());
        assertEquals("5", capturedTourLog.getRating());
        assertEquals("Info about the tour log", capturedTourLog.getInfo());
        assertEquals(mockTour, capturedTourLog.getTour());

        // Verify fields are cleared after adding
        assertTrue(viewModel.nameProperty().get().isEmpty());
        assertTrue(viewModel.dateProperty().get().isEmpty());
        assertTrue(viewModel.ratingProperty().get().isEmpty());
        assertTrue(viewModel.infoProperty().get().isEmpty());
    }
}
