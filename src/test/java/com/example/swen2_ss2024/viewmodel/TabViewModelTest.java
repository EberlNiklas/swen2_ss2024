package com.example.swen2_ss2024.viewmodel;

import static org.junit.jupiter.api.Assertions.*;
import com.example.swen2_ss2024.event.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

}
