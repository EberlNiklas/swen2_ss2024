package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.entity.Tours;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.ObjectSubscriber;
import com.example.swen2_ss2024.viewmodel.TourViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class TourView implements ObjectSubscriber {

    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonMore;
    @FXML
    private Button buttonShowAll;
    @FXML
    private ListView<String> tourList;

    private final TourViewModel tourViewModel;

    public TourView(TourViewModel tourViewModel) {
        this.tourViewModel = tourViewModel;
        tourViewModel.getPublisher().subscribe(Event.SEARCH_RESULT, this);
        tourViewModel.getPublisher().subscribe(Event.RESET_SEARCH, this);
    }

    @FXML
    public void initialize() {
        buttonAdd.setOnAction(e -> tourViewModel.onAdd());
        buttonDelete.setOnAction(e -> tourViewModel.deleteSelectedTour());
        buttonMore.setOnAction(e -> tourViewModel.onMore());
        buttonShowAll.setOnAction(e -> tourViewModel.onShowAll());

        tourList.setItems(tourViewModel.getTourNames());
        this.tourViewModel.selectedIndex().bind(tourList.getSelectionModel().selectedIndexProperty());

        buttonShowAll.disableProperty().bind(tourViewModel.showAllDisabledProperty());

        tourList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                tourViewModel.selectTour(newVal);
            }
        });
    }

    @Override
    public void notify(Object message) {
        if (message instanceof Tours) {
            Tours tour = (Tours) message;
            tourViewModel.updateTourListWithSearchResult(tour);
            // Optionally scroll to and select the tour in the list
            if (tour != null) {
                tourList.getSelectionModel().select(tour.getName());
                tourList.scrollTo(tour.getName());
                tourViewModel.setShowAllDisabled(false);
            }
        } else if (message == Event.RESET_SEARCH) {
            System.out.println("Handling RESET_SEARCH in TourView"); // Debug line
            tourViewModel.loadToursFromDatabase();
            tourViewModel.setShowAllDisabled(true);
            tourList.setItems(tourViewModel.getTourNames());  // Ensure the ListView is updated
        }
    }
}
