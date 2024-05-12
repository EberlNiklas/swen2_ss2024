package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.viewmodel.TabViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TabView implements Initializable {
    @FXML
    private ImageView routeImageView;
    @FXML
    private ListView<String> detailsList;

    private final TabViewModel tabViewModel;

    @FXML
    private VBox routeContainer;

    public TabView(TabViewModel tabViewModel) {
        this.tabViewModel = tabViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        detailsList.setItems(tabViewModel.getTourDetails());
        routeImageView.imageProperty().bind(tabViewModel.routeImageProperty());

        routeContainer.widthProperty().addListener((obs, oldVal, newVal) -> {
            routeImageView.setFitWidth(newVal.doubleValue() * 0.95); // 95% of container width
        });
        routeContainer.heightProperty().addListener((obs, oldVal, newVal) -> {
            routeImageView.setFitHeight(newVal.doubleValue() * 0.95); // 95% of container height
        });

        routeImageView.setPreserveRatio(true);
    }


}

