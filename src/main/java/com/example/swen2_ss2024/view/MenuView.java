package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.viewmodel.MenuViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuView implements Initializable {

    @FXML
    private MenuItem fileButton;

    @FXML
    private MenuItem editButton;

    @FXML
    private MenuItem deleteButton;

    public final MenuViewModel menuViewModel;

    public MenuView (MenuViewModel menuViewModel){
        this.menuViewModel = menuViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
