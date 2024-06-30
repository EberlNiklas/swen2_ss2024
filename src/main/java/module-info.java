module com.example.swen2_ss2024 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires org.apache.logging.log4j;
    requires com.google.gson;
    requires java.desktop;

    opens com.example.swen2_ss2024.entity to org.hibernate.orm.core;
    opens com.example.swen2_ss2024 to javafx.fxml;
    opens com.example.swen2_ss2024.view to javafx.fxml;
    exports com.example.swen2_ss2024;
}