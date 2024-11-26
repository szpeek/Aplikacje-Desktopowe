package com.example.fileform;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;


import java.nio.file.Paths;
import java.nio.file.Files;

public class fileForm extends Application {
    @Override
    public void start(Stage stage) {
        Text text1 = new Text("Ścieżka odczytu :");


        TextField sciezka = new TextField();
        TextField wyjscie = new TextField();

        TextField zapisField = new TextField();


        Button button1 = new Button("Odczytaj");
        Button button2 = new Button("Zapisz");


        GridPane gridPane = new GridPane();

        gridPane.setMinSize(700, 200);

        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.setVgap(5);
        gridPane.setHgap(5);

        wyjscie.setPrefSize(500,200);
        zapisField.setPrefSize(500,200);
        Line line = new Line(0, 0, 700,0);

        gridPane.add(text1, 1, 0);
        gridPane.add(sciezka, 1, 1);
        gridPane.add(button1, 1, 2);
        gridPane.add(wyjscie, 1,5);
        gridPane.add(line, 1,10);
        gridPane.add(zapisField,1,13);
        gridPane.add(button2,1,14);


        //odczyt
        button1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                String filePath = sciezka.getText();

                try {
                    System.out.println("Odczyt pliku: " + filePath);

                    java.nio.file.Path path = java.nio.file.Paths.get(filePath);

                    List<String> fileContent = Files.readAllLines(path);

                    String tekst = String.join("\n", fileContent);
                    wyjscie.setText(tekst);

                } catch (IOException e) {
                    System.err.println("Wystąpił błąd podczas pracy z plikiem: " + e.getMessage());
                }


            }
        });

        //zapis
        button2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String filePath2 = "output.txt";

                try {
                    System.out.println("\nZapis do pliku: " + filePath2);
                    java.nio.file.Path path = java.nio.file.Paths.get(filePath2);

                    List<String> lines = Collections.singletonList(zapisField.getText());
                    Files.write(Path.of(filePath2), lines);

                } catch (IOException e){
                    System.err.println("Wystąpił błąd podczas pracy z plikiem: " + e.getMessage());
                }
            }
        });

                Scene scene = new Scene(gridPane, 750, 300);

                stage.setTitle("Pliki");

                stage.setScene(scene);

                stage.show();
            }



            public static void main(String args[]){
                launch(args);
            }
        }