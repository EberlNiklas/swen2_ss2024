package com.example.swen2_ss2024.view;
import com.example.swen2_ss2024.viewmodel.AddTourViewModel ;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
public class AddTourView implements Initializable{
    @FXML private VBox rootPane;
    private final AddTourViewModel viewModel;

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
    private Button addTourButton;
    @FXML
    private TextField imagePathField;



    public AddTourView(AddTourViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Binding of the properties to the text fields
        nameField.textProperty().bindBidirectional(viewModel.nameProperty());
        descriptionField.textProperty().bindBidirectional(viewModel.descriptionProperty());
        fromField.textProperty().bindBidirectional(viewModel.fromProperty());
        toField.textProperty().bindBidirectional(viewModel.toProperty());
        transportTypeField.textProperty().bindBidirectional(viewModel.transportTypeProperty());
        distanceField.textProperty().bindBidirectional(viewModel.distanceProperty());
        estimatedTimeField.textProperty().bindBidirectional(viewModel.estimatedTimeProperty());
        imagePathField.textProperty().bindBidirectional(viewModel.imagePathProperty());

        // Binding of button disable property
        addTourButton.disableProperty().bind(viewModel.addTourButtonDisabledProperty());
    }

    @FXML
    public void addTour() {
        viewModel.addTour();
        // Closes the view
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}
