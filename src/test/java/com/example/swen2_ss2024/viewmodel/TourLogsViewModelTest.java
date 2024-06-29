package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.entity.TourLog;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.service.NewTourLogService;
import com.example.swen2_ss2024.service.TourListService;
import com.example.swen2_ss2024.service.TourLogListService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

public class TourLogsViewModelTest {

    private TourLogsViewModel viewModel;
    private Publisher publisher;
    private TourLogListService tourLogListService;
    private TourListService tourListService;
    private NewTourLogService newTourLogService;

    @BeforeEach
    public void setUp() {
        publisher = mock(Publisher.class);
        tourLogListService = mock(TourLogListService.class);
        tourListService = mock(TourListService.class);
        newTourLogService = mock(NewTourLogService.class);
        viewModel = new TourLogsViewModel(publisher, tourLogListService, tourListService);
    }

    @Test
    public void testLoadTourLogs() {
        // Arrange
        TourLog log1 = new TourLog();
        TourLog log2 = new TourLog();
        when(tourLogListService.getAllTourLogs()).thenReturn(List.of(log1, log2));

        // Act
        viewModel.loadTourLogs();

        // Assert
        ObservableList<TourLog> tourLogs = viewModel.getTourLogs();
        assertEquals(2, tourLogs.size(), "There should be 2 tour logs loaded.");
        assertTrue(tourLogs.contains(log1), "Tour log 1 should be in the list.");
        assertTrue(tourLogs.contains(log2), "Tour log 2 should be in the list.");
    }

    @Test
    public void testAddToTourLogs() {
        // Arrange
        TourLog newLog = new TourLog();

        // Act
        viewModel.addToTourLogs(newLog);

        // Assert
        ObservableList<TourLog> tourLogs = viewModel.getTourLogs();
        assertTrue(tourLogs.contains(newLog), "New tour log should be added to the list.");
    }

    @Test
    public void testDeleteTourLog() {
        // Arrange
        TourLog log1 = new TourLog();
        log1.setId(1L);
        TourLog log2 = new TourLog();
        log2.setId(2L);
        viewModel.getTourLogs().addAll(log1, log2);
        viewModel.selectedAddTourProperty().set(0);
        when(tourLogListService.delete(1L)).thenReturn(true);

        // Act
        viewModel.delete();

        // Assert
        ObservableList<TourLog> tourLogs = viewModel.getTourLogs();
        assertEquals(1, tourLogs.size(), "There should be 1 tour log left after deletion.");
        assertFalse(tourLogs.contains(log1), "Tour log 1 should be removed from the list.");
    }

    @Test
    public void testUpdateTourLogs() {
        // Arrange
        TourLog log1 = new TourLog();
        log1.setId(1L);
        TourLog log2 = new TourLog();
        log2.setId(2L);
        when(tourLogListService.getTourLogsByTourId(1L)).thenReturn(List.of(log1, log2));

        // Act
        viewModel.updateTourLogs(1L);

        // Assert
        ObservableList<TourLog> tourLogs = viewModel.getTourLogs();
        assertEquals(2, tourLogs.size(), "There should be 2 tour logs loaded for the tour.");
        assertTrue(tourLogs.contains(log1), "Tour log 1 should be in the list.");
        assertTrue(tourLogs.contains(log2), "Tour log 2 should be in the list.");
    }
}
