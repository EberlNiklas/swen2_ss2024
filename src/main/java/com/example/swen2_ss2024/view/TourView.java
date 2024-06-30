package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.entity.Tours;
import com.example.swen2_ss2024.viewmodel.TourViewModel;
import com.example.swen2_ss2024.event.Event;
import com.example.swen2_ss2024.event.ObjectSubscriber;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class TourView implements ObjectSubscriber, Initializable {

    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonMore;
    @FXML
    private Button buttonShowAll;
    @FXML
    private ListView<Tours> tourList;

    private final TourViewModel tourViewModel;

    public TourView(TourViewModel tourViewModel) {
        this.tourViewModel = tourViewModel;
        tourViewModel.getPublisher().subscribe(Event.SEARCH_RESULT, this);
        tourViewModel.getPublisher().subscribe(Event.RESET_SEARCH, this);
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonAdd.setOnAction(e -> tourViewModel.onAdd());
        buttonDelete.setOnAction(e -> tourViewModel.delete());
        buttonMore.setOnAction(e -> tourViewModel.onMore());
        buttonShowAll.setOnAction(e -> tourViewModel.onShowAll());

        tourList.setItems(tourViewModel.getTourList());
        tourViewModel.selectIndex().bind(tourList.getSelectionModel().selectedIndexProperty());

        buttonShowAll.disableProperty().bind(tourViewModel.showAllDisabledProperty());

        tourList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                tourViewModel.selectTour(tourList.getSelectionModel().getSelectedIndex());
            }
        });

        tourList.setCellFactory(lv -> new TextFieldListCell<>(new StringConverter<Tours>() {
            @Override
            public String toString(Tours tour) {
                return tour.getName();
            }

            @Override
            public Tours fromString(String string) {
                return null;
            }
        }));

        // Add double-click event listener
        tourList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Tours selectedTour = tourList.getSelectionModel().getSelectedItem();
                if (selectedTour != null) {
                    tourViewModel.duplicateTour(selectedTour);
                }
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
                tourList.getSelectionModel().select(tour);
                tourList.scrollTo(tour);
                tourViewModel.setShowAllDisabled(false);
            }
        } else if (message == Event.RESET_SEARCH) {
            System.out.println("Handling RESET_SEARCH in TourView"); // Debug line
            tourViewModel.loadToursFromDatabase();
            tourViewModel.setShowAllDisabled(true);
        }
    }
}
