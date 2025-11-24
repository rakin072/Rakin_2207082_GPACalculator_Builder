package com.example.cgpacalculator;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
            DatabaseHelper.deleteHistory(selected.rollProperty().get(), selected.courseListProperty().get());
            historyData.remove(selected);
        }
    }

    private void clearAllHistory() {
        DatabaseHelper.clearHistory();
        historyData.clear();
    }

    private void goBackToCalculator() {
        // TODO: Implement navigation back to the calculator view
    }


    public static class HistoryEntry {
        private final SimpleStringProperty roll;
        private final SimpleStringProperty gpa;
        private final SimpleStringProperty courseList;

        public HistoryEntry(String roll, String gpa, String courseList) {
            this.roll = new SimpleStringProperty(roll);
            this.gpa = new SimpleStringProperty(gpa);
            this.courseList = new SimpleStringProperty(courseList);
        }

        public SimpleStringProperty rollProperty() { return roll; }
        public SimpleStringProperty gpaProperty() { return gpa; }
        public SimpleStringProperty courseListProperty() { return courseList; }
    }
}
