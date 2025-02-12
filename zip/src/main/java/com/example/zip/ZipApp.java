package com.example.zip;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button zipButton = new Button("Spakuj pliki");
        Button unzipButton = new Button("Rozpakuj ZIP");
        Label statusLabel = new Label("Status: Oczekiwanie na akcję...");

        zipButton.setOnAction(e -> zipFiles(primaryStage, statusLabel));
        unzipButton.setOnAction(e -> unzipFile(primaryStage, statusLabel));

        VBox layout = new VBox(10, zipButton, unzipButton, statusLabel);

        VBox.setMargin(zipButton, new Insets(10,10,10,10));
        VBox.setMargin(unzipButton, new Insets(10,10,10,10));
        VBox.setMargin(zipButton, new Insets(20,20,20,20));

        layout.setStyle("-fx-padding: 20px; -fx-alignment: center; -fx-background-color: #88b5fc");
        zipButton.setStyle("-fx-border-color: #000000; -fx-border-width: 2px;");
        unzipButton.setStyle("-fx-border-color: #000000; -fx-border-width: 2px;");
        statusLabel.setStyle("-fx-border-color: #000000; -fx-border-width: 1px;");
        Scene scene = new Scene(layout, 400, 200);

        primaryStage.setTitle("ZIP Kompresja & Dekompresja");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void zipFiles(Stage stage, Label statusLabel) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz pliki do spakowania");
        List<File> files = fileChooser.showOpenMultipleDialog(stage);

        if (files == null || files.isEmpty()) {
            statusLabel.setText("Nie wybrano plików.");
            return;
        }

        FileChooser saveDialog = new FileChooser();
        saveDialog.setTitle("Zapisz plik ZIP");
        saveDialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archiwum ZIP", "*.zip"));
        File zipFile = saveDialog.showSaveDialog(stage);

        if (zipFile == null) {
            statusLabel.setText("Nie wybrano lokalizacji.");
            return;
        }

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            for (File file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zos.putNextEntry(zipEntry);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                }
            }
            statusLabel.setText("Pliki spakowane do: " + zipFile.getAbsolutePath());
        } catch (IOException ex) {
            statusLabel.setText("Błąd: " + ex.getMessage());
        }
    }

    private void unzipFile(Stage stage, Label statusLabel) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik ZIP do rozpakowania");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archiwum ZIP", "*.zip"));
        File zipFile = fileChooser.showOpenDialog(stage);

        if (zipFile == null) {
            statusLabel.setText("Nie wybrano pliku ZIP.");
            return;
        }

        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Wybierz lokalizację do rozpakowania");
        File outputDir = dirChooser.showDialog(stage);

        if (outputDir == null) {
            statusLabel.setText("Nie wybrano lokalizacji docelowej.");
            return;
        }

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                File newFile = new File(outputDir, zipEntry.getName());
                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                }
            }
            statusLabel.setText("Plik ZIP rozpakowany do: " + outputDir.getAbsolutePath());
        } catch (IOException ex) {
            statusLabel.setText("Błąd: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}