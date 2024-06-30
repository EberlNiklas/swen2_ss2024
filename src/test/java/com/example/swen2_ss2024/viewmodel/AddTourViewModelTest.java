package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.entity.Tours;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.service.OpenRouteService;
import com.example.swen2_ss2024.service.TourListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddTourViewModelTest {

    private Publisher publisher;
    private TourListService tourListService;
    private OpenRouteService routeService;
    private AddTourViewModel viewModel;

    @BeforeEach
    public void setUp() {
        publisher = mock(Publisher.class);
        tourListService = mock(TourListService.class);
        routeService = mock(OpenRouteService.class);
        viewModel = new AddTourViewModel(publisher, tourListService);
    }

    @Test
    public void testInitialState() {
        assertTrue(viewModel.addTourButtonDisabledProperty().get());
        assertEquals("", viewModel.nameProperty().get());
        assertEquals("", viewModel.descriptionProperty().get());
        assertEquals("", viewModel.fromProperty().get());
        assertEquals("", viewModel.toProperty().get());
        assertEquals("", viewModel.transportTypeProperty().get());
        assertEquals(0, viewModel.distanceProperty().get(), 0.001);
        assertEquals(0, viewModel.estimatedTimeProperty().get(), 0.001);
        assertEquals("", viewModel.imagePathProperty().get());
    }

    @Test
    public void testAddTourButtonEnabledWhenFieldsAreNotEmpty() {
        viewModel.nameProperty().set("Tour Name");
        viewModel.descriptionProperty().set("Tour Description");
        viewModel.fromProperty().set("Origin");
        viewModel.toProperty().set("Destination");
        viewModel.transportTypeProperty().set("Car");

        assertFalse(viewModel.addTourButtonDisabledProperty().get());
    }

    @Test
    public void testAddTourButtonDisabledWhenFieldsAreEmpty() {
        assertTrue(viewModel.addTourButtonDisabledProperty().get());

        viewModel.nameProperty().set("Tour Name");
        assertTrue(viewModel.addTourButtonDisabledProperty().get());

        viewModel.descriptionProperty().set("Tour Description");
        assertTrue(viewModel.addTourButtonDisabledProperty().get());

        viewModel.fromProperty().set("Origin");
        assertTrue(viewModel.addTourButtonDisabledProperty().get());

        viewModel.toProperty().set("Destination");
        assertTrue(viewModel.addTourButtonDisabledProperty().get());

        viewModel.transportTypeProperty().set("");
        assertTrue(viewModel.addTourButtonDisabledProperty().get());
    }

    @Test
    public void testAddTour() {
        viewModel.nameProperty().set("Tour Name");
        viewModel.descriptionProperty().set("Tour Description");
        viewModel.fromProperty().set("Origin");
        viewModel.toProperty().set("Destination");
        viewModel.transportTypeProperty().set("Car");

        assertFalse(viewModel.addTourButtonDisabledProperty().get());

        viewModel.addTour();

        verify(tourListService, times(1)).addTour(any(Tours.class));
        verify(publisher, times(1)).publish(eq(Event.TOUR_ADDED), any(Tours.class));

        assertEquals("", viewModel.nameProperty().get());
        assertEquals("", viewModel.descriptionProperty().get());
        assertEquals("", viewModel.fromProperty().get());
        assertEquals("", viewModel.toProperty().get());
        assertEquals("", viewModel.transportTypeProperty().get());
        assertEquals(0, viewModel.distanceProperty().get(), 0.001);
        assertEquals(0, viewModel.estimatedTimeProperty().get(), 0.001);
        assertEquals("", viewModel.imagePathProperty().get());
    }
}
