package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.entity.Tours;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.service.TourListService;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
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
    public void testEditTour() {
        // Arrange
        Tours tour = new Tours("Name", "Description", "From", "To", "Transport", "Distance", "Time", "ImagePath");
        tour.setId(1L);
        viewModel.setTour(tour);

        viewModel.nameProperty().set("New Name");
        viewModel.descriptionProperty().set("New Description");
        viewModel.fromProperty().set("New From");
        viewModel.toProperty().set("New To");
        viewModel.transportTypeProperty().set("New Transport");
        viewModel.distanceProperty().set("New Distance");
        viewModel.estimatedTimeProperty().set("New Time");
        viewModel.imagePathProperty().set("New ImagePath");

        // Act
        viewModel.editTour();

        // Assert
        ArgumentCaptor<Tours> captor = ArgumentCaptor.forClass(Tours.class);
        verify(tourListService).updateTour(captor.capture());
        Tours updatedTour = captor.getValue();

        assertEquals("New Name", updatedTour.getName());
        assertEquals("New Description", updatedTour.getDescription());
        assertEquals("New From", updatedTour.getFrom());
        assertEquals("New To", updatedTour.getTo());
        assertEquals("New Transport", updatedTour.getTransportType());
        assertEquals("New Distance", updatedTour.getDistance());
        assertEquals("New Time", updatedTour.getEstimatedTime());
        assertEquals("New ImagePath", updatedTour.getImagePath());
    }

    @Test
    public void testUpdateEditTourButtonDisabled() {
        // Act & Assert
        assertTrue(viewModel.editTourButtonDisabledProperty().get(), "Button should be disabled initially when fields are empty");

        viewModel.nameProperty().set("Name");
        viewModel.descriptionProperty().set("Description");
        viewModel.fromProperty().set("From");
        viewModel.toProperty().set("To");
        viewModel.transportTypeProperty().set("Transport");
        viewModel.distanceProperty().set("Distance");
        viewModel.estimatedTimeProperty().set("Time");
        viewModel.imagePathProperty().set("ImagePath");

        assertFalse(viewModel.editTourButtonDisabledProperty().get(), "Button should be enabled when all fields are filled");

        viewModel.nameProperty().set("");
        assertTrue(viewModel.editTourButtonDisabledProperty().get(), "Button should be disabled when a required field is empty");
    }

    @Test
    public void testSetImagePath() {
        // Act
        viewModel.setImagePath("new/path/to/image.jpg");

        // Assert
        assertEquals("new/path/to/image.jpg", viewModel.imagePathProperty().get());
        verify(publisher).publish(Event.IMAGE_PATH_UPDATED, "new/path/to/image.jpg");

        // Act - setting image path to null
        viewModel.setImagePath("");

        // Assert
        assertNull(viewModel.imagePathProperty().get());
        verify(publisher).publish(Event.IMAGE_PATH_UPDATED, null);
    }
}
