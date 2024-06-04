package com.example.swen2_ss2024;


import com.example.swen2_ss2024.database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

public class TourPlannerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent mainView = FXMLDependencyInjector.load("main-view.fxml", Locale.ENGLISH);
        Scene scene = new Scene(mainView);
        stage.setTitle("Tour Planner");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try {
            Database.createTourTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        launch();
    }

}