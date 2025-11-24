module com.example.cgpacalculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires com.google.gson;

    opens com.example.cgpacalculator to javafx.fxml;
    opens com.example.cgpacalculator.model to com.google.gson;
    exports com.example.cgpacalculator;
}