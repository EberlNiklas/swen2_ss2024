package com.example.swen2_ss2024.service;
import com.example.swen2_ss2024.FXMLDependencyInjector ;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
public class NewTourService {
    public void loadFXML(String fxmlPath) {
        try {
            // Load the FXML file for the new stage
            Parent newStageView = FXMLDependencyInjector.load(fxmlPath, Locale.ENGLISH);

            // Create a new stage
            Stage newStage = new Stage();
            newStage.setTitle("New Stage");

            // Set the scene for the new stage
            Scene scene = new Scene(newStageView);
            newStage.setScene(scene);

            // Show the new stage
            newStage.show();
        } catch (IOException e) {
            // Handle any potential exceptions when loading FXML
            e.printStackTrace();
        }
    }

}
