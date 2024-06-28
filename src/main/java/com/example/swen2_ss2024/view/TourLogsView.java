package com.example.swen2_ss2024.view;


import com.example.swen2_ss2024.entity.TourLog;
import com.example.swen2_ss2024.service.TourListService;
import com.example.swen2_ss2024.viewmodel.TourLogsViewModel;
import com.example.swen2_ss2024.viewmodel.TourViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class TourLogsView{

    @FXML
    private Button tourLogAdd;
    @FXML
    private Button tourLogDelete;
    @FXML
    private Button tourLogEdit;

    @FXML
    private TableView<TourLog> tourLogList;

    @FXML
    private TableColumn<TourLog, String> nameColumn;
    @FXML
    private TableColumn<TourLog, String> dateColumn;
    @FXML
    private TableColumn<TourLog, String> ratingColumn;
    @FXML
    private TableColumn<TourLog, String> infoColumn;
    @FXML
    private TableColumn<TourLog, String> distanceColumn;
    @FXML
    private TableColumn<TourLog, String> durationColumn;

    private final TourLogsViewModel tourLogsViewModel;


    public TourLogsView(TourLogsViewModel tourLogsViewModel) {
        this.tourLogsViewModel = tourLogsViewModel;
    }

    @FXML
    public void initialize() {
        tourLogAdd.setOnAction(e -> tourLogsViewModel.onAdd());
        tourLogEdit.setOnAction(e -> tourLogsViewModel.onMore());
        tourLogDelete.setOnAction(e -> tourLogsViewModel.delete());

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        distanceColumn.setCellValueFactory(cellData -> cellData.getValue().distanceProperty());
        durationColumn.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
        ratingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty());
        infoColumn.setCellValueFactory(cellData -> cellData.getValue().infoProperty());

        tourLogList.setItems(tourLogsViewModel.getTourLogs());
        tourLogsViewModel.selectedAddTourProperty().bind(tourLogList.getSelectionModel().selectedIndexProperty());

    }
}
