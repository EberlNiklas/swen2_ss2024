package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.entity.TourLog;
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
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class EditTourLogView implements Initializable {

    private final EditTourLogViewModel viewModel;

    @FXML
    private TextField nameField;
    @FXML private TextField dateField;
    @FXML private TextField durationField;
    @FXML private TextField distanceField;
    @FXML private TextField ratingField;
    @FXML private TextField infoField;
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
        ratingField.textProperty().bindBidirectional(viewModel.ratingProperty());
        infoField.textProperty().bindBidirectional(viewModel.infoProperty());

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
        ratingField.addEventHandler(KeyEvent.KEY_PRESSED, enterKeyHandler);
        infoField.addEventHandler(KeyEvent.KEY_PRESSED, enterKeyHandler);

    }

    @FXML
    public void handleEditTourLog() {
        viewModel.modifyTourLog();
        Stage stage = (Stage) editTourLogButton.getScene().getWindow();
        stage.close();
    }
}
