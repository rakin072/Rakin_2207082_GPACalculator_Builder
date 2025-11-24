package com.example.cgpacalculator;

import com.example.cgpacalculator.model.Course;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private static final String URL = "jdbc:sqlite:ResultInfo.db";
    private static final Gson gson = new Gson();


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }


    public static void createHistoryTable() {
        String query = """
            CREATE TABLE IF NOT EXISTS history (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                roll TEXT NOT NULL,
                gpa TEXT NOT NULL,
                courseList TEXT NOT NULL
            )
            """;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void insertHistory(String roll, double gpa, List<Course> courses) {
        String query = "INSERT INTO history (roll, gpa, courseList) VALUES (?, ?, ?)";
        String courseJson = gson.toJson(courses); // convert course list to JSON

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, roll);
            pstmt.setString(2, String.format("%.2f", gpa)); // store GPA as string
            pstmt.setString(3, courseJson);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static List<HistoryController.HistoryEntry> getAllHistory() {
        List<HistoryController.HistoryEntry> list = new ArrayList<>();
        String query = "SELECT roll, gpa, courseList FROM history ORDER BY id DESC";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String roll = rs.getString("roll");
                String gpa = rs.getString("gpa");
                String courseJson = rs.getString("courseList");
                List<Course> courses = gson.fromJson(courseJson, new TypeToken<List<Course>>(){}.getType());
                String courseSummary = buildCourseSummary(courses);
                list.add(new HistoryController.HistoryEntry(roll, gpa, courseSummary));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public static List<HistoryController.HistoryEntry> getHistoryByRoll(String roll) {
        List<HistoryController.HistoryEntry> list = new ArrayList<>();
        String query = "SELECT roll, gpa, courseList FROM history WHERE roll = ? ORDER BY id DESC";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, roll);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String gpa = rs.getString("gpa");
                    String courseJson = rs.getString("courseList");
                    List<Course> courses = gson.fromJson(courseJson, new TypeToken<List<Course>>(){}.getType());
                    String courseSummary = buildCourseSummary(courses);
                    list.add(new HistoryController.HistoryEntry(roll, gpa, courseSummary));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public static void deleteHistory(String roll, String courseSummary) {
        String query = "DELETE FROM history WHERE roll = ? AND courseList LIKE ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, roll);
            pstmt.setString(2, "%" + courseSummary + "%");
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void clearHistory() {
        String query = "DELETE FROM history";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static String buildCourseSummary(List<Course> courses) {
        StringBuilder sb = new StringBuilder();
        for (Course c : courses) {
            sb.append(c.getCode()).append("(").append(c.getGrade()).append("), ");
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 2); // remove last comma
        return sb.toString();
    }
}
