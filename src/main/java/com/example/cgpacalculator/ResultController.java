package com.example.cgpacalculator;

import com.example.cgpacalculator.model.Course;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.text.DecimalFormat;

public class ResultController {

    @FXML
    private Label resultLabel;

    @FXML
    private TableView<Course> resultTable;

    @FXML
    private TableColumn<Course, String> colName;

    @FXML
    private TableColumn<Course, String> colCode;

    @FXML
    private TableColumn<Course, Double> colCredit;

    @FXML
    private TableColumn<Course, String> colTeacher1;

    @FXML
    private TableColumn<Course, String> colTeacher2;

    @FXML
    private TableColumn<Course, String> colGrade;

    private final DecimalFormat df = new DecimalFormat("0.00");

    @FXML
    public void initialize() {
        // Bind Table Columns
        colName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        colCode.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCode()));
        colCredit.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getCredit()).asObject());
        colTeacher1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTeacher1()));
        colTeacher2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTeacher2()));
        colGrade.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGrade()));
    }

    public void setCourses(ObservableList<Course> courses) {

        if (courses == null || courses.isEmpty()) {
            resultLabel.setText("No courses added.");
            return;
        }

        resultTable.setItems(courses);

        double totalCredits = 0;
        double totalPoints = 0;

        for (Course course : courses) {
            double credit = course.getCredit();
            double point = gradeToPoint(course.getGrade());

            totalCredits += credit;
            totalPoints += credit * point;
        }

        double gpa = totalPoints / totalCredits;

        resultLabel.setText(
                "Total Credits: " + df.format(totalCredits) + "\n" +
                        "GPA: " + df.format(gpa)
        );
    }

    private double gradeToPoint(String grade) {
        return switch (grade) {
            case "A+" -> 4.00;
            case "A"  -> 3.75;
            case "A-" -> 3.50;
            case "B+" -> 3.25;
            case "B"  -> 3.00;
            case "B-" -> 2.75;
            case "C+" -> 2.50;
            case "C"  -> 2.25;
            case "D"  -> 2.00;
            case "E"  -> 1.75;
            default   -> 0.0;
        };
    }
}
