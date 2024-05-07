package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.viewmodel.TourLogsViewModel;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class TourLogsView implements Initializable {

    private final TourLogsViewModel tourLogsViewModel;

    public TourLogsView (TourLogsViewModel tourLogsViewModel){
        this.tourLogsViewModel = tourLogsViewModel;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
