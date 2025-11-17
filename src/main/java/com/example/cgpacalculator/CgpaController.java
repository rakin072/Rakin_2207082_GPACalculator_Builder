package com.example.cgpacalculator;

import com.example.cgpacalculator.model.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;

public class CgpaController {

    @FXML private TextField courseName;
    @FXML private TextField courseCode;
    @FXML private TextField credit;
    @FXML private TextField teacher1;
    @FXML private TextField teacher2;
    @FXML private ComboBox<String> grade;
    @FXML private TableView<Course> table;
    @FXML private TableColumn<Course, String> colName;
    @FXML private TableColumn<Course, String> colCode;
    @FXML private TableColumn<Course, Double> colCredit;
    @FXML private TableColumn<Course, String> colTeacher1;
    @FXML private TableColumn<Course, String> colTeacher2;
    @FXML private TableColumn<Course, String> colGrade;
    @FXML private Button addCourseButton;
    @FXML private Button calculateButton;
    @FXML private Label remainingCreditsLabel;

    private ObservableList<Course> courses = FXCollections.observableArrayList();

    private final double REQUIRED_TOTAL_CREDITS = 15.00;
    private double currentTotalCredits = 0.0;

    @FXML
    public void initialize() {
        grade.setItems(FXCollections.observableArrayList(
                "A+", "A", "A-", "B+", "B", "B-", "C+", "C", "D", "F"
        ));

        colName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        colCode.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCode()));
        colCredit.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getCredit()).asObject());
        colTeacher1.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTeacher1()));
        colTeacher2.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTeacher2()));
        colGrade.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getGrade()));

        table.setItems(courses);

        calculateButton.setDisable(true);
        updateRemainingCredits();
    }


    private void updateRemainingCredits() {
        double remaining = REQUIRED_TOTAL_CREDITS - currentTotalCredits;
        if (remaining < 0) remaining = 0;

        remainingCreditsLabel.setText("Remaining Credits: " + String.format("%.2f", remaining));
    }


    @FXML
    protected void onAddCourse() {

        if (courseName.getText().isEmpty() ||
                courseCode.getText().isEmpty() ||
                credit.getText().isEmpty() ||
                grade.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Data");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
            return;
        }

        double creditValue;
        try {
            creditValue = Double.parseDouble(credit.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Credit must be a valid number.");
            alert.showAndWait();
            return;
        }

        courses.add(new Course(
                courseName.getText(),
                courseCode.getText(),
                creditValue,
                teacher1.getText(),
                teacher2.getText(),
                grade.getValue()
        ));

        currentTotalCredits += creditValue;
        updateRemainingCredits();

        if (currentTotalCredits >= REQUIRED_TOTAL_CREDITS) {
            addCourseButton.setDisable(true);
            credit.setDisable(true);
            courseName.setDisable(true);
            courseCode.setDisable(true);
            teacher1.setDisable(true);
            teacher2.setDisable(true);
            grade.setDisable(true);

            calculateButton.setDisable(false);
        }

        courseName.clear();
        courseCode.clear();
        credit.clear();
        teacher1.clear();
        teacher2.clear();
        grade.setValue(null);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Course added successfully!");
        alert.showAndWait();
    }


    @FXML
    protected void onRemoveCourse() {
        Course selectedCourse = table.getSelectionModel().getSelectedItem();
        if (selectedCourse == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a course to remove.");
            alert.showAndWait();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Deletion");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to remove the selected course?");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                courses.remove(selectedCourse);


                currentTotalCredits = courses.stream().mapToDouble(Course::getCredit).sum();
                updateRemainingCredits();


                if (currentTotalCredits < REQUIRED_TOTAL_CREDITS) {
                    addCourseButton.setDisable(false);
                    credit.setDisable(false);
                    courseName.setDisable(false);
                    courseCode.setDisable(false);
                    teacher1.setDisable(false);
                    teacher2.setDisable(false);
                    grade.setDisable(false);
                    calculateButton.setDisable(true);
                }

                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Removed");
                info.setHeaderText(null);
                info.setContentText("Course removed successfully.");
                info.showAndWait();
            }
        });
    }


    @FXML
    protected void onCalculate(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cgpacalculator/result-view.fxml"));
        Parent root = loader.load();

        ResultController controller = loader.getController();
        controller.setCourses(courses);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
