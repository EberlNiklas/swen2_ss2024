package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.entity.Tours;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.service.TourListService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddTourViewModelTest {

    private AddTourViewModel viewModel;
    private Publisher publisher;
    private TourListService tourListService;

    @BeforeEach
    public void beforeEach() {
        publisher = mock(Publisher.class);
        tourListService = mock(TourListService.class);
        viewModel = new AddTourViewModel(publisher, tourListService);
    }

    @Test
    public void testInitialState() {
        assertTrue(viewModel.addTourButtonDisabledProperty().get(), "Add Tour button should be disabled initially.");
    }

    @Test
    public void testAddTourButtonDisabledWhenFieldsAreEmpty() {
        viewModel.nameProperty().set("");
        viewModel.descriptionProperty().set("");
        viewModel.fromProperty().set("");
        viewModel.toProperty().set("");
        viewModel.transportTypeProperty().set("");
        viewModel.distanceProperty().set("");
        viewModel.estimatedTimeProperty().set("");
        viewModel.imagePathProperty().set("");
        assertTrue(viewModel.addTourButtonDisabledProperty().get(), "Add Tour button should be disabled when fields are empty.");
    }

    @Test
    public void testAddTourButtonEnabledWhenFieldsAreNotEmpty() {
        viewModel.nameProperty().set("Tour Name");
        viewModel.descriptionProperty().set("Description");
        viewModel.fromProperty().set("From");
        viewModel.toProperty().set("To");
        viewModel.transportTypeProperty().set("Transport Type");
        viewModel.distanceProperty().set("Distance");
        viewModel.estimatedTimeProperty().set("Estimated Time");
        viewModel.imagePathProperty().set("Image Path");
        assertFalse(viewModel.addTourButtonDisabledProperty().get(), "Add Tour button should be enabled when all fields are filled.");
    }

    @Test
    public void testAddTour() {
        viewModel.nameProperty().set("Tour Name");
        viewModel.descriptionProperty().set("Description");
        viewModel.fromProperty().set("From");
        viewModel.toProperty().set("To");
        viewModel.transportTypeProperty().set("Transport Type");
        viewModel.distanceProperty().set("Distance");
        viewModel.estimatedTimeProperty().set("Estimated Time");
        viewModel.imagePathProperty().set("Image Path");

        viewModel.addTour();

        ArgumentCaptor<Tours> tourCaptor = ArgumentCaptor.forClass(Tours.class);
        verify(tourListService).addTour(tourCaptor.capture());
        verify(publisher).publish(eq(Event.TOUR_ADDED), tourCaptor.capture());

        Tours capturedTour = tourCaptor.getValue();
        assertEquals("Tour Name", capturedTour.getName());
        assertEquals("Description", capturedTour.getDescription());
        assertEquals("From", capturedTour.getFrom());
        assertEquals("To", capturedTour.getTo());
        assertEquals("Transport Type", capturedTour.getTransportType());
        assertEquals("Distance", capturedTour.getDistance());
        assertEquals("Estimated Time", capturedTour.getEstimatedTime());
        assertEquals("Image Path", capturedTour.getImagePath());

        // Verify fields are cleared after adding
        assertTrue(viewModel.nameProperty().get().isEmpty());
        assertTrue(viewModel.descriptionProperty().get().isEmpty());
        assertTrue(viewModel.fromProperty().get().isEmpty());
        assertTrue(viewModel.toProperty().get().isEmpty());
        assertTrue(viewModel.transportTypeProperty().get().isEmpty());
        assertTrue(viewModel.distanceProperty().get().isEmpty());
        assertTrue(viewModel.estimatedTimeProperty().get().isEmpty());
        assertTrue(viewModel.imagePathProperty().get().isEmpty());
    }
}
