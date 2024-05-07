package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.viewmodel.TourViewModel;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class TourView implements Initializable {

    private final TourViewModel tourViewModel;

    public TourView (TourViewModel tourViewModel){
        this.tourViewModel = tourViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
