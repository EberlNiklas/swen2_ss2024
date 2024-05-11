package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.viewmodel.TabViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class TabView  {
    @FXML
    private ListView<String> detailsList;

    private final TabViewModel tabViewModel;

    public TabView(TabViewModel tabViewModel) {
        this.tabViewModel = tabViewModel;
    }

    @FXML
    public void initialize() {
        detailsList.setItems(tabViewModel.getTourDetails());
    }
}
