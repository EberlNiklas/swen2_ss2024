package com.example.swen2_ss2024.viewmodel;
import com.example.swen2_ss2024.entity.Tours;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.service.NewTourService;
import com.example.swen2_ss2024.service.TourListService;
import com.example.swen2_ss2024.service.TourLogListService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TourViewModelTest {

    private TourViewModel viewModel;
    private Publisher publisher;
    private TourListService tourListService;
    private TourLogListService tourLogListService;
    private NewTourService newTourService;

    @BeforeEach
    public void beforeEach() {
        publisher = mock(Publisher.class);
        tourListService = mock(TourListService.class);
        tourLogListService = mock(TourLogListService.class);
        newTourService = mock(NewTourService.class);
        viewModel = new TourViewModel(publisher, tourListService, tourLogListService);
    }

    @Test
    public void testInitialState() {
        ObservableList<String> tourNames = viewModel.getTourNames();
        assertNotNull(tourNames);
        assertTrue(tourNames.isEmpty());
        assertTrue(viewModel.showAllDisabledProperty().get(), "Show All button should be disabled initially.");
    }

    @Test
    public void testLoadToursFromDatabase() {
        // Arrange
        Tours mockTour1 = new Tours();
        mockTour1.setName("Tour 1");
        Tours mockTour2 = new Tours();
        mockTour2.setName("Tour 2");
        when(tourListService.getTours()).thenReturn(FXCollections.observableArrayList(mockTour1, mockTour2));

        // Act
        viewModel.loadToursFromDatabase();

        // Assert
        ObservableList<String> tourNames = viewModel.getTourNames();
        assertEquals(2, tourNames.size());
        assertTrue(tourNames.contains("Tour 1"));
        assertTrue(tourNames.contains("Tour 2"));
    }

    @Test
    public void testUpdateTourListWithSearchResult() {
        // Arrange
        Tours searchResult = new Tours();
        searchResult.setName("Test Tour");

        // Act
        viewModel.updateTourListWithSearchResult(searchResult);

        // Assert
        ObservableList<String> tourNames = viewModel.getTourNames();
        assertEquals(1, tourNames.size());
        assertEquals("Test Tour", tourNames.get(0));
        assertFalse(viewModel.showAllDisabledProperty().get(), "Show All button should be enabled after search.");
    }
}
