package com.example.swen2_ss2024.view;
import com.example.swen2_ss2024.viewmodel.EditTourViewModel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class EditTourView implements Initializable{
    private final EditTourViewModel viewModel;

    @FXML private TextField nameField;
    @FXML private TextField descriptionField;
    @FXML private TextField fromField;
    @FXML private TextField toField;
    @FXML private TextField transportTypeField;
    @FXML private TextField distanceField;
    @FXML private TextField estimatedTimeField;
    @FXML private Button editTourButton;

    public EditTourView(EditTourViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Binding properties to text fields
        nameField.textProperty().bindBidirectional(viewModel.nameProperty());
        descriptionField.textProperty().bindBidirectional(viewModel.descriptionProperty());
        fromField.textProperty().bindBidirectional(viewModel.fromProperty());
        toField.textProperty().bindBidirectional(viewModel.toProperty());
        transportTypeField.textProperty().bindBidirectional(viewModel.transportTypeProperty());
        distanceField.textProperty().bindBidirectional(viewModel.distanceProperty());
        estimatedTimeField.textProperty().bindBidirectional(viewModel.estimatedTimeProperty());

        // Button bindings
        editTourButton.disableProperty().bind(viewModel.editTourButtonDisabledProperty());
    }

    @FXML
    public void editTour() {
        viewModel.editTour();
    }
}
