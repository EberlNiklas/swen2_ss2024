package com.example.swen2_ss2024.viewmodel;

import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.models.Tour;
import com.example.swen2_ss2024.service.TourListService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TourViewModelTest {

    private TourViewModel viewModel;

    @BeforeEach
    public void setUp() {
        Publisher publisher = new Publisher();
        TourListService tourListService = new TourListService();
        viewModel = new TourViewModel(publisher, tourListService);
    }
    @Test
    public void testSelectTour_ValidIndex() {
        viewModel.selectIndex().set(1);
        viewModel.selectIndex().addListener((obs, oldVal, newVal) -> {
            assertEquals(Integer.valueOf(1), newVal);
            assertEquals("Tour 2", viewModel.getTourList().get(newVal.intValue()).getName());
        });
    }

    @Test
    public void testSelectTour_InvalidIndex() {
        viewModel.selectIndex().set(-1);
        viewModel.selectIndex().addListener((obs, oldVal, newVal) -> {
            assertEquals(Integer.valueOf(-1), newVal);
            assertTrue(newVal.intValue() < 0); // Verifies that no valid index is selected
        });
    }

    @Test
    public void testDelete_InvalidIndex() {
        int initialSize = viewModel.getTourList().size();
        viewModel.selectIndex().set(-1); // Select an invalid index
        viewModel.delete();
        assertEquals(initialSize, viewModel.getTourList().size()); // Assert no item was removed
    }

}