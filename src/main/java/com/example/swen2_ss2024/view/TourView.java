package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.entity.Tours;
import com.example.swen2_ss2024.viewmodel.TourViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

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

    }
}
