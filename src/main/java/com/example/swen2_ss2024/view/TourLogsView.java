package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.models.TourLog;
import com.example.swen2_ss2024.viewmodel.TourLogsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.SelectionMode;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class TourLogsView implements Initializable {

    @FXML
    private ListView<TourLog> tourLogList;
    private TourLogsViewModel tourLogsViewModel;

    private ObservableList<TourLog> tourLogs = FXCollections.observableArrayList();

    public TourLogsView(TourLogsViewModel tourLogsViewModel) {
        this.tourLogsViewModel = tourLogsViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourLogList.setItems(tourLogs);
        tourLogList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    protected void handleAddTourLog() {
        // Later
    }

    @FXML
    protected void handleEditTourLog() {
        // Later
    }

    @FXML
    protected void handleDeleteTourLog() {
        TourLog selectedTourLog = tourLogList.getSelectionModel().getSelectedItem();
        if (selectedTourLog != null) {
            tourLogs.remove(selectedTourLog);
        } else {
            showAlert("Please select a tour log to delete.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
