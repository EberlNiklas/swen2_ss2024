package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.viewmodel.TourViewModel;
import com.example.swen2_ss2024.models.Tour;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

public class TourView {

    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonMore;

    @FXML
    private ListView<Tour> tourList;

    private final TourViewModel tourViewModel;

    public TourView(TourViewModel tourViewModel) {
        this.tourViewModel = tourViewModel;
    }

    @FXML
    public void initialize() {
        buttonAdd.setOnAction(e -> tourViewModel.onAdd());
        buttonDelete.setOnAction(e -> tourViewModel.delete());
        buttonMore.setOnAction(e -> tourViewModel.onMore());

        tourList.setItems(tourViewModel.getTourList());
        tourViewModel.selectIndex().bind(tourList.getSelectionModel().selectedIndexProperty());

        // Sets up how each tour is displayed in the ListView
        tourList.setCellFactory(lv -> new TextFieldListCell<>(new StringConverter<Tour>() {
            @Override
            public String toString(Tour tour) {
                return tour.getName();
            }

            @Override
            public Tour fromString(String string) {
                return null;
            }
        }));
    }
}
