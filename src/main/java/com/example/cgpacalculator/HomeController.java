package com.example.cgpacalculator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;

public class HomeController {

    @FXML
    protected void onStart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cgpacalculator/gpa-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private Parent rootPane;

    @FXML
    private void onToggleTheme() {
        if (rootPane.getStyleClass().contains("background-light")) {
            rootPane.getStyleClass().remove("background-light");
            rootPane.getStyleClass().add("background-dark");
        } else {
            rootPane.getStyleClass().remove("background-dark");
            rootPane.getStyleClass().add("background-light");
        }
    }

}
