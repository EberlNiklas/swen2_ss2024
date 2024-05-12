package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.models.Tour;
import com.example.swen2_ss2024.models.TourLog;
import com.example.swen2_ss2024.viewmodel.TourLogsViewModel;
import com.example.swen2_ss2024.viewmodel.TourViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.SelectionMode;
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
    private ListView<TourLog> tourLogList;

    private final TourLogsViewModel tourLogsViewModel;

    public TourLogsView(TourLogsViewModel tourLogsViewModel) {
        this.tourLogsViewModel = tourLogsViewModel;
    }

    @FXML
    public void initialize() {
        tourLogAdd.setOnAction(e -> tourLogsViewModel.onAdd());
        tourLogDelete.setOnAction(e -> tourLogsViewModel.delete());
        tourLogEdit.setOnAction(e -> tourLogsViewModel.onMore());

        tourLogList.setItems(tourLogsViewModel.getTourList());
        tourLogsViewModel.selectIndex().bind(tourLogList.getSelectionModel().selectedIndexProperty());

        // Sets up how each tour is displayed in the ListView
        tourLogList.setCellFactory(lv -> new TextFieldListCell<>(new StringConverter<TourLog>() {
            @Override
            public String toString(TourLog tourLog) {
                return tourLog.getName();
            }

            @Override
            public TourLog fromString(String string) {
                return null;
            }
        }));
    }
}
