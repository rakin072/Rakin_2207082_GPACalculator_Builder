package com.example.cgpacalculator;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class HistoryController {

    @FXML
    private TextField rollField;

    @FXML
    private TableView<HistoryEntry> historyTable;

    @FXML
    private TableColumn<HistoryEntry, String> rollColumn;

    @FXML
    private TableColumn<HistoryEntry, String> gpaColumn;

    @FXML
    private TableColumn<HistoryEntry, String> courseListColumn;

    @FXML
    private Button searchRollButton, showAllButton, backButton, removeSelectedButton, clearAllButton;

    private ObservableList<HistoryEntry> historyData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        rollColumn.setCellValueFactory(cellData -> cellData.getValue().rollProperty());
        gpaColumn.setCellValueFactory(cellData -> cellData.getValue().gpaProperty());
        courseListColumn.setCellValueFactory(cellData -> cellData.getValue().courseListProperty());

        loadAllHistory();

        searchRollButton.setOnAction(e -> searchByRoll());
        showAllButton.setOnAction(e -> loadAllHistory());
        removeSelectedButton.setOnAction(e -> removeSelectedEntry());
        clearAllButton.setOnAction(e -> clearAllHistory());
        backButton.setOnAction(e -> goBackToCalculator());
    }

    private void loadAllHistory() {
        historyData.clear();
        historyData.addAll(DatabaseHelper.getAllHistory());
        historyTable.setItems(historyData);
    }

    private void searchByRoll() {
        String roll = rollField.getText().trim();
        if (roll.isEmpty()) return;
        historyData.clear();
        historyData.addAll(DatabaseHelper.getHistoryByRoll(roll));
        historyTable.setItems(historyData);
    }

    private void removeSelectedEntry() {
        HistoryEntry selected = historyTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            DatabaseHelper.deleteHistoryById(selected.getId());
            historyData.remove(selected);
        }
    }

    private void clearAllHistory() {
        DatabaseHelper.clearHistory();
        historyData.clear();
    }

    private void goBackToCalculator() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cgpacalculator/gpa-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow(); // get current window
            stage.setScene(new Scene(root));
            stage.setTitle("GPA Calculator");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class HistoryEntry {
        private final int id; // new ID field
        private final SimpleStringProperty roll;
        private final SimpleStringProperty gpa;
        private final SimpleStringProperty courseList;

        public HistoryEntry(int id, String roll, String gpa, String courseList) {
            this.id = id;
            this.roll = new SimpleStringProperty(roll);
            this.gpa = new SimpleStringProperty(gpa);
            this.courseList = new SimpleStringProperty(courseList);
        }

        public int getId() { return id; } // getter for ID
        public SimpleStringProperty rollProperty() { return roll; }
        public SimpleStringProperty gpaProperty() { return gpa; }
        public SimpleStringProperty courseListProperty() { return courseList; }
    }
}
