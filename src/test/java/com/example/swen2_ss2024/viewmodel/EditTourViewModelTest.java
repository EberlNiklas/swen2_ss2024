package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.entity.Tours;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.service.TourListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EditTourViewModelTest {

    private EditTourViewModel viewModel;
    private Publisher publisher;
    private TourListService tourListService;

    @BeforeEach
    public void setUp() {
        publisher = mock(Publisher.class);
        tourListService = mock(TourListService.class);
        viewModel = new EditTourViewModel(publisher, tourListService);
    }

    @Test
    public void testInitialState() {
        assertTrue(viewModel.editTourButtonDisabledProperty().get(), "Edit Tour button should be disabled initially.");
        assertEquals("", viewModel.nameProperty().get());
        assertEquals("", viewModel.descriptionProperty().get());
        assertEquals("", viewModel.fromProperty().get());
        assertEquals("", viewModel.toProperty().get());
        assertEquals("", viewModel.transportTypeProperty().get());
        assertEquals("", viewModel.imagePathProperty().get());
    }

    @Test
    public void testUpdateEditTourButtonDisabled() {
        viewModel.nameProperty().set("Tour Name");
        viewModel.descriptionProperty().set("Tour Description");
        viewModel.fromProperty().set("Origin");
        viewModel.toProperty().set("Destination");
        viewModel.transportTypeProperty().set("Car");
        viewModel.imagePathProperty().set("image/path");

        assertFalse(viewModel.editTourButtonDisabledProperty().get(), "Edit Tour button should be enabled when all fields are filled.");

        viewModel.nameProperty().set("");
        assertTrue(viewModel.editTourButtonDisabledProperty().get(), "Edit Tour button should be disabled when a required field is empty.");
    }

    @Test
    public void testEditTour() {
        Tours tour = new Tours("Old Name", "Old Description", "Old From", "Old To", "Car", 100, 120, "old/path");
        tour.setId(1L);

        viewModel.setTour(tour);

        viewModel.nameProperty().set("New Name");
        viewModel.descriptionProperty().set("New Description");
        viewModel.fromProperty().set("New From");
        viewModel.toProperty().set("New To");
        viewModel.transportTypeProperty().set("Bicycle");
        viewModel.imagePathProperty().set("new/path");

        viewModel.editTour();

        ArgumentCaptor<Tours> captor = ArgumentCaptor.forClass(Tours.class);
        verify(tourListService).updateTour(captor.capture());
        Tours updatedTour = captor.getValue();

        assertEquals(1L, updatedTour.getId());
        assertEquals("New Name", updatedTour.getName());
        assertEquals("New Description", updatedTour.getDescription());
        assertEquals("New From", updatedTour.getFrom());
        assertEquals("New To", updatedTour.getTo());
        assertEquals("Bicycle", updatedTour.getTransportType());
        assertEquals("new/path", updatedTour.getImagePath());

        verify(publisher).publish(Event.TOUR_UPDATED, updatedTour);
    }
}
