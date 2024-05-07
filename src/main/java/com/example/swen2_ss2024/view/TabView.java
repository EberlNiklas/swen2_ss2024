package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.viewmodel.TabViewModel;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class TabView implements Initializable {

    public final TabViewModel tabViewModel;

    public TabView (TabViewModel tabViewModel){
        this.tabViewModel = tabViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
