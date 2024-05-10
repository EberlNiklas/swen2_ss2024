package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.viewmodel.TourViewModel;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TourView  {

    @FXML
    private Button buttonAdd;
    @FXML private Button buttonDelete;
    @FXML private Button buttonMore;

    private final TourViewModel viewModel;

    public TourView(TourViewModel tourViewModel) {
        this.viewModel = tourViewModel;
    }

    @FXML
    public void initialize() {
        buttonAdd.setOnAction(e -> viewModel.onAdd());
        buttonDelete.setOnAction(e -> viewModel.onDelete());
        buttonMore.setOnAction(e -> viewModel.onMore());
    }
}
