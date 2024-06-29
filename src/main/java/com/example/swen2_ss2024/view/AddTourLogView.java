package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.viewmodel.AddTourLogViewModel;
import com.example.swen2_ss2024.viewmodel.AddTourViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTourLogView implements Initializable {

    @FXML
    private VBox rootPane;
    private final AddTourLogViewModel viewModel;

    @FXML
    private TextField nameField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField infoField;

    @FXML
    private Button addTourLogButton;

    @FXML
    private TextField ratingField;

    public AddTourLogView(AddTourLogViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Binding of the properties to the text fields
        nameField.textProperty().bindBidirectional(viewModel.nameProperty());
        dateField.textProperty().bindBidirectional(viewModel.dateProperty());
        infoField.textProperty().bindBidirectional(viewModel.infoProperty());
        ratingField.textProperty().bindBidirectional(viewModel.ratingProperty());


        // Binding of button disable property
        addTourLogButton.disableProperty().bind(viewModel.addTourLogButtonDisabledProperty());
    }
    @FXML
    public void addTourLog() {
        viewModel.addTourLog();
        // Closes the view
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}
