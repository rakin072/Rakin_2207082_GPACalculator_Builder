package com.example.cgpacalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CgpaApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home-view.fxml"));
        Parent root = fxmlLoader.load();

        root.getStyleClass().removeAll("background-light", "background-dark");

        root.getStyleClass().add("background-light");

        Scene scene = new Scene(root);
        stage.setTitle("CGPA Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
