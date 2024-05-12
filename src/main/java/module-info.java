module com.example.swen2_ss2024 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;



    opens com.example.swen2_ss2024 to javafx.fxml;
    opens com.example.swen2_ss2024.view to javafx.fxml;
    exports com.example.swen2_ss2024;
}