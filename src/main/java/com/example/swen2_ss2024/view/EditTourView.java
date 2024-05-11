package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.models.Tour;
import com.example.swen2_ss2024.viewmodel.EditTourViewModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;



public class EditTourView implements Initializable {

    private final EditTourViewModel viewModel;

    @FXML private TextField nameField;
    @FXML private TextField descriptionField;
    @FXML private TextField fromField;
    @FXML private TextField toField;
    @FXML private TextField transportTypeField;
    @FXML private TextField distanceField;
    @FXML private TextField estimatedTimeField;
    @FXML private Button editTourButton;

    public EditTourView(EditTourViewModel viewModel) {  // Accept viewModel as a constructor parameter
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameField.textProperty().bindBidirectional(viewModel.nameProperty());
        descriptionField.textProperty().bindBidirectional(viewModel.descriptionProperty());
        fromField.textProperty().bindBidirectional(viewModel.fromProperty());
        toField.textProperty().bindBidirectional(viewModel.toProperty());
        transportTypeField.textProperty().bindBidirectional(viewModel.transportTypeProperty());
        distanceField.textProperty().bindBidirectional(viewModel.distanceProperty());
        estimatedTimeField.textProperty().bindBidirectional(viewModel.estimatedTimeProperty());

        editTourButton.setOnAction(e -> handleEditTour());

        // Sets up Enter key to trigger Edit action for each TextField
        EventHandler<KeyEvent> enterKeyHandler = event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleEditTour();
                event.consume();  // Prevents the event from propagating further
            }
        };

        // Applies the handler to all text fields
        nameField.addEventHandler(KeyEvent.KEY_PRESSED, enterKeyHandler);
        descriptionField.addEventHandler(KeyEvent.KEY_PRESSED, enterKeyHandler);
        fromField.addEventHandler(KeyEvent.KEY_PRESSED, enterKeyHandler);
        toField.addEventHandler(KeyEvent.KEY_PRESSED, enterKeyHandler);
        transportTypeField.addEventHandler(KeyEvent.KEY_PRESSED, enterKeyHandler);
        distanceField.addEventHandler(KeyEvent.KEY_PRESSED, enterKeyHandler);
        estimatedTimeField.addEventHandler(KeyEvent.KEY_PRESSED, enterKeyHandler);
    }


    public void setTour(Tour tour) {
        viewModel.setTour(tour);
    }

    @FXML
    public void handleEditTour() {
        viewModel.editTour();
        Stage stage = (Stage) editTourButton.getScene().getWindow();
        stage.close(); // Closes the window after editing
    }


}
