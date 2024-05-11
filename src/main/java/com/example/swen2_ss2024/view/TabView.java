package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.viewmodel.TabViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;

import java.net.URL;
import java.util.ResourceBundle;

public class TabView implements Initializable {
    @FXML
    private ImageView routeImageView;
    @FXML
    private ListView<String> detailsList;

    private final TabViewModel tabViewModel;

    public TabView(TabViewModel tabViewModel) {
        this.tabViewModel = tabViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        detailsList.setItems(tabViewModel.getTourDetails());
        routeImageView.imageProperty().bind(tabViewModel.routeImageProperty());

        routeImageView.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observableValue, Scene oldScene, Scene newScene) {
                if (newScene != null) {
                    routeImageView.setPreserveRatio(true);
                    routeImageView.fitWidthProperty().bind(newScene.widthProperty());
                    routeImageView.fitHeightProperty().bind(newScene.heightProperty());
                }
            }
        });
    }


}

