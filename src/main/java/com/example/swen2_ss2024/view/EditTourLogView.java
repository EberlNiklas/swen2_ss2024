package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.models.Tour;
import com.example.swen2_ss2024.models.TourLog;
import com.example.swen2_ss2024.viewmodel.EditTourLogViewModel;
import com.example.swen2_ss2024.viewmodel.EditTourViewModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditTourLogView implements Initializable {

    private final EditTourLogViewModel viewModel;

    @FXML
    private TextField nameField;
    @FXML private TextField dateField;
    @FXML private TextField durationField;
    @FXML private TextField distanceField;
    @FXML private TextField commentField;
    @FXML private TextField ratingField;
    @FXML private TextField difficultyField;
    @FXML private Button editTourLogButton;

    public EditTourLogView(EditTourLogViewModel viewModel) {  // Accept viewModel as a constructor parameter
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameField.textProperty().bindBidirectional(viewModel.nameProperty());
        dateField.textProperty().bindBidirectional(viewModel.dateProperty());
        durationField.textProperty().bindBidirectional(viewModel.durationProperty());
        distanceField.textProperty().bindBidirectional(viewModel.distanceProperty());
        commentField.textProperty().bindBidirectional(viewModel.commentProperty());
        ratingField.textProperty().bindBidirectional(viewModel.ratingProperty());
        difficultyField.textProperty().bindBidirectional(viewModel.difficultyProperty());

        editTourLogButton.setOnAction(e -> handleEditTourLog());

        // Sets up Enter key to trigger Edit action for each TextField
        EventHandler<KeyEvent> enterKeyHandler = event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleEditTourLog();
                event.consume();  // Prevents the event from propagating further
            }
        };

        // Applies the handler to all text fields
        nameField.addEventHandler(KeyEvent.KEY_PRESSED, enterKeyHandler);
        dateField.addEventHandler(KeyEvent.KEY_PRESSED, enterKeyHandler);
        durationField.addEventHandler(KeyEvent.KEY_PRESSED, enterKeyHandler);
        distanceField.addEventHandler(KeyEvent.KEY_PRESSED, enterKeyHandler);
        commentField.addEventHandler(KeyEvent.KEY_PRESSED, enterKeyHandler);
        ratingField.addEventHandler(KeyEvent.KEY_PRESSED, enterKeyHandler);
        difficultyField.addEventHandler(KeyEvent.KEY_PRESSED, enterKeyHandler);

    }


    public void setTourLog(TourLog tourLog) {
        viewModel.setTourLog(tourLog);
    }

    @FXML
    public void handleEditTourLog() {
        viewModel.editTourLog();
        Stage stage = (Stage) editTourLogButton.getScene().getWindow();
        stage.close();
    }
}
