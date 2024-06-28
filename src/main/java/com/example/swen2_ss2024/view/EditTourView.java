package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.ViewFactory;
import com.example.swen2_ss2024.entity.Tours;
import com.example.swen2_ss2024.viewmodel.EditTourViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditTourView implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField fromField;
    @FXML
    private TextField toField;
    @FXML
    private TextField transportTypeField;
    @FXML
    private TextField distanceField;
    @FXML
    private TextField estimatedTimeField;
    @FXML
    private TextField imagePathField;

    private final EditTourViewModel editTourViewModel;

    public EditTourView(EditTourViewModel editTourViewModel) {
        this.editTourViewModel = editTourViewModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindFields();
    }

    @FXML
    private void handleEditTour() {
        if (imagePathField.getText() == null || imagePathField.getText().isEmpty()) {
            System.out.println("Image path must not be empty");
            return;
        }
        editTourViewModel.editTour();
    }

    private void bindFields() {
        nameField.textProperty().bindBidirectional(editTourViewModel.nameProperty());
        descriptionField.textProperty().bindBidirectional(editTourViewModel.descriptionProperty());
        fromField.textProperty().bindBidirectional(editTourViewModel.fromProperty());
        toField.textProperty().bindBidirectional(editTourViewModel.toProperty());
        transportTypeField.textProperty().bindBidirectional(editTourViewModel.transportTypeProperty());
        distanceField.textProperty().bindBidirectional(editTourViewModel.distanceProperty());
        estimatedTimeField.textProperty().bindBidirectional(editTourViewModel.estimatedTimeProperty());
        imagePathField.textProperty().bindBidirectional(editTourViewModel.imagePathProperty());
    }
}
