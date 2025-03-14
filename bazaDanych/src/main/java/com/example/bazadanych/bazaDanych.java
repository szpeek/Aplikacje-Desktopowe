package com.example.bazadanych;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class bazaDanych extends Application {
    private static final String URL = "jdbc:mysql://localhost:3306/school";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private TextField tableNameField = new TextField();
    private TextField nameField = new TextField();
    private TextField ageField = new TextField();
    private TextField gradeField = new TextField();
    private TextField idField = new TextField();
    private TextArea outputArea = new TextArea();
    private ComboBox<String> tableSelector = new ComboBox<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label tableNameLabel = new Label("Table Name:");
        Button createTableButton = new Button("Create Table");
        createTableButton.setOnAction(e -> createTable());

        Label tableSelectorLabel = new Label("Select Table:");
        updateTableList();

        Label idLabel = new Label("ID:");
        Label nameLabel = new Label("Name:");
        Label ageLabel = new Label("Age:");
        Label gradeLabel = new Label("Grade:");
        Button insertButton = new Button("Insert Record");
        Button fetchButton = new Button("Fetch Records");
        Button updateButton = new Button("Update Record");
        Button deleteButton = new Button("Delete Record");

        insertButton.setOnAction(e -> insertRecord());
        fetchButton.setOnAction(e -> fetchRecords());
        updateButton.setOnAction(e -> updateRecord());
        deleteButton.setOnAction(e -> deleteRecord());

        root.getChildren().addAll(tableNameLabel, tableNameField, createTableButton, tableSelectorLabel, tableSelector, idLabel, idField, nameLabel, nameField, ageLabel, ageField, gradeLabel, gradeField, insertButton, fetchButton, updateButton, deleteButton, outputArea);
        Scene scene = new Scene(root, 400, 600);
        primaryStage.setTitle("Database Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createTable() {
        String tableName = tableNameField.getText();
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), age INT, grade VARCHAR(10))";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            outputArea.appendText("Table " + tableName + " created successfully!\n");
            updateTableList();
        } catch (SQLException e) {
            outputArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }

    private void insertRecord() {
        String tableName = tableSelector.getValue();
        if (tableName == null || tableName.isEmpty()) {
            outputArea.appendText("Please select a table first!\n");
            return;
        }
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String grade = gradeField.getText();

        String sql = "INSERT INTO " + tableName + " (name, age, grade) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, grade);
            stmt.executeUpdate();
            outputArea.appendText("Record added to " + tableName + " successfully!\n");
        } catch (SQLException e) {
            outputArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }

    private void fetchRecords() {
        String tableName = tableSelector.getValue();
        if (tableName == null || tableName.isEmpty()) {
            outputArea.appendText("Please select a table first!\n");
            return;
        }
        String sql = "SELECT * FROM " + tableName;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            outputArea.clear();
            while (rs.next()) {
                outputArea.appendText("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Age: " + rs.getInt("age") + ", Grade: " + rs.getString("grade") + "\n");
            }
        } catch (SQLException e) {
            outputArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }

    private void updateRecord() {
        String tableName = tableSelector.getValue();
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String grade = gradeField.getText();

        String sql = "UPDATE " + tableName + " SET name=?, age=?, grade=? WHERE id=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, grade);
            stmt.setInt(4, id);
            stmt.executeUpdate();
            outputArea.appendText("Record updated successfully!\n");
        } catch (SQLException e) {
            outputArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }

    private void deleteRecord() {
        String tableName = tableSelector.getValue();
        int id = Integer.parseInt(idField.getText());

        String sql = "DELETE FROM " + tableName + " WHERE id=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            outputArea.appendText("Record deleted successfully!\n");
        } catch (SQLException e) {
            outputArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }

    private void updateTableList() {
        tableSelector.getItems().clear();
        String sql = "SHOW TABLES";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tableSelector.getItems().add(rs.getString(1));
            }
        } catch (SQLException e) {
            outputArea.appendText("Error retrieving table list: " + e.getMessage() + "\n");
        }
    }
}
