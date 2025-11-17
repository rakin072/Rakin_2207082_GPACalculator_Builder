package com.example.cgpacalculator;

import com.example.cgpacalculator.model.Course;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.text.DecimalFormat;

public class ResultController {

    @FXML
    private Label resultLabel;

    private final DecimalFormat df = new DecimalFormat("0.00");

    public void setCourses(ObservableList<Course> courses) {
        if (courses == null || courses.isEmpty()) {
            resultLabel.setText("No courses added.");
            return;
        }

        double totalCredits = 0.0;
        double totalPoints = 0.0;

        for (Course course : courses) {
            double credit = course.getCredit();
            double point = gradeToPoint(course.getGrade());
            totalCredits += credit;
            totalPoints += credit * point;
        }

        double gpa = totalPoints / totalCredits;


        StringBuilder sb = new StringBuilder();
        sb.append("Total Credits: ").append(df.format(totalCredits)).append("\n");
        sb.append("GPA: ").append(df.format(gpa));

        resultLabel.setText(sb.toString());
    }

    private double gradeToPoint(String grade) {
        return switch (grade) {
            case "A+", "A" -> 4.0;
            case "A-" -> 3.7;
            case "B+" -> 3.3;
            case "B" -> 3.0;
            case "B-" -> 2.7;
            case "C+" -> 2.3;
            case "C" -> 2.0;
            case "D" -> 1.0;
            default -> 0.0;
        };
    }
}
