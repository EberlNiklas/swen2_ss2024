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
        assertEquals("", viewModel.nameProperty().get());
        assertEquals("", viewModel.dateProperty().get());
        assertEquals("", viewModel.ratingProperty().get());
        assertEquals("", viewModel.infoProperty().get());
        assertEquals("", viewModel.distanceProperty().get());
        assertEquals("", viewModel.durationProperty().get());
    }

    @Test
    public void testAddTourLogButtonEnabledWhenFieldsAreNotEmpty() {
        viewModel.nameProperty().set("Tour Log Name");
        viewModel.dateProperty().set("2023-06-30");
        viewModel.ratingProperty().set("5");
        viewModel.infoProperty().set("Good tour");
        viewModel.distanceProperty().set("10 km");
        viewModel.durationProperty().set("1 hour");

        assertFalse(viewModel.addTourLogButtonDisabledProperty().get(), "Add Tour Log button should be enabled when all fields are filled.");
    }

    @Test
    public void testAddTourLog() {
        Tours selectedTour = new Tours();
        when(tourListService.selected()).thenReturn(true);
        when(tourListService.getSelectedTour()).thenReturn(selectedTour);

        viewModel.nameProperty().set("Tour Log Name");
        viewModel.dateProperty().set("2023-06-30");
        viewModel.ratingProperty().set("5");
        viewModel.infoProperty().set("Good tour");
        viewModel.distanceProperty().set("10 km");
        viewModel.durationProperty().set("1 hour");

        viewModel.addTourLog();

        ArgumentCaptor<TourLog> captor = ArgumentCaptor.forClass(TourLog.class);
        verify(tourLogListService).addTourLog(captor.capture());
        TourLog capturedTourLog = captor.getValue();

        assertEquals("Tour Log Name", capturedTourLog.getName());
        assertEquals("2023-06-30", capturedTourLog.getDate());
        assertEquals("5", capturedTourLog.getRating());
        assertEquals("Good tour", capturedTourLog.getInfo());
        assertEquals("10 km", capturedTourLog.getDistance());
        assertEquals("1 hour", capturedTourLog.getDuration());
        assertEquals(selectedTour, capturedTourLog.getTour());

        verify(publisher).publish(Event.TOUR_LOG_ADDED, capturedTourLog);

        assertEquals("", viewModel.nameProperty().get());
        assertEquals("", viewModel.dateProperty().get());
        assertEquals("", viewModel.ratingProperty().get());
        assertEquals("", viewModel.infoProperty().get());
        assertEquals("", viewModel.distanceProperty().get());
        assertEquals("", viewModel.durationProperty().get());
        assertTrue(viewModel.addTourLogButtonDisabledProperty().get(), "Add Tour Log button should be disabled after adding a log.");
    }

    @Test
    public void testAddTourLogButtonDisabledWhenFieldsAreEmpty() {
        assertTrue(viewModel.addTourLogButtonDisabledProperty().get(), "Add Tour Log button should be disabled when fields are empty.");

        viewModel.nameProperty().set("Tour Log Name");
        assertTrue(viewModel.addTourLogButtonDisabledProperty().get(), "Add Tour Log button should be disabled when fields are partially filled.");

        viewModel.dateProperty().set("2023-06-30");
        assertTrue(viewModel.addTourLogButtonDisabledProperty().get(), "Add Tour Log button should be disabled when fields are partially filled.");

        viewModel.ratingProperty().set("5");
        assertTrue(viewModel.addTourLogButtonDisabledProperty().get(), "Add Tour Log button should be disabled when fields are partially filled.");

        viewModel.infoProperty().set("Good tour");
        assertTrue(viewModel.addTourLogButtonDisabledProperty().get(), "Add Tour Log button should be disabled when fields are partially filled.");

        viewModel.distanceProperty().set("10 km");
        assertTrue(viewModel.addTourLogButtonDisabledProperty().get(), "Add Tour Log button should be disabled when fields are partially filled.");

        viewModel.durationProperty().set("1 hour");
        assertFalse(viewModel.addTourLogButtonDisabledProperty().get(), "Add Tour Log button should be enabled when all fields are filled.");
    }
}
