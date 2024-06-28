package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.viewmodel.TourViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class TourView {

    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonMore;
    @FXML
    private ListView<String> tourList;

    private final TourViewModel tourViewModel;

    public TourView(TourViewModel tourViewModel) {
        this.tourViewModel = tourViewModel;
    }

    @FXML
    public void initialize() {
        buttonAdd.setOnAction(e -> tourViewModel.onAdd());
        buttonDelete.setOnAction(e -> tourViewModel.deleteSelectedTour());
        buttonMore.setOnAction(e -> tourViewModel.onMore());

        tourList.setItems(tourViewModel.getTourNames());
        this.tourViewModel.selectedIndex().bind(tourList.getSelectionModel().selectedIndexProperty());

        tourList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                tourViewModel.selectTour(newVal);
            }
        });
    }
}
