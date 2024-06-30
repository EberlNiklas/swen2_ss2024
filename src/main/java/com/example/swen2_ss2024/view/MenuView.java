package com.example.swen2_ss2024.view;

import com.example.swen2_ss2024.pdfGenerator.TourLogPDFGenerator;
import com.example.swen2_ss2024.pdfGenerator.TourPDFGenerator;
import com.example.swen2_ss2024.viewmodel.MenuViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuView implements Initializable {

    @FXML
    private MenuItem fileButton;

    @FXML
    private MenuItem editButton;

    @FXML
    private MenuItem deleteButton;

    @FXML
    private MenuItem menuItemSave; // Add this line

    public final MenuViewModel menuViewModel;

    public MenuView(MenuViewModel menuViewModel) {
        this.menuViewModel = menuViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuItemSave.setOnAction(e -> {
            try {
                if (menuViewModel.isTourSelected()) {
                    TourPDFGenerator pdfGenerator = new TourPDFGenerator(menuViewModel.getTourListService());
                    pdfGenerator.createPdfWithMap();
                } else if (menuViewModel.isTourLogSelected()) {
                    TourLogPDFGenerator pdfGenerator = new TourLogPDFGenerator(menuViewModel.getTourLogListService());
                    pdfGenerator.createPdfWithMap();
                } else {
                    System.out.println("No tour or tour log selected.");
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }
}
