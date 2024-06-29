package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.viewmodel.SearchViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


import java.net.URL;
import java.util.ResourceBundle;

public class SearchBarView implements Initializable {

    public final SearchViewModel searchViewModel;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    public SearchBarView(SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.searchField.textProperty().bindBidirectional(searchViewModel.searchTextProperty());
        this.searchButton.disableProperty().bind(searchViewModel.searchDisabledProperty());
    }

    @FXML
    public void onSearch() {
        this.searchViewModel.search();
    }
}