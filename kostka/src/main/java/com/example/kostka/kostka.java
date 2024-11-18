package com.example.kostka;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.event.EventHandler;

import java.util.Random;

public class kostka extends Application {
    @Override
    public void start(Stage stage) {
        Label lbl1 = new Label("Podaj liczbe z przedziału 3-10");
        TextField text1 = new TextField();
        Button wyslij = new Button("Wyslij");



        GridPane gridPane = new GridPane();


        gridPane.add(lbl1,0,0);
        gridPane.add(text1, 0,1);
        gridPane.add(wyslij,0,2);

        gridPane.setMinSize(500, 700);


        Group groupKostka = new Group();



        gridPane.getChildren().add(groupKostka);

        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.setVgap(5);
        gridPane.setHgap(5);

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                String out1 = text1.getText();
                Integer out2 = Integer.parseInt(out1);
                if (out2 <= 10 && out2 >= 3) {

                    gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) >= 4);

                    VBox resultsBox = new VBox();
                    resultsBox.setSpacing(10);
                    gridPane.add(resultsBox, 0, 4, 3, 1);

                    Random random = new Random();

                    int wynik = 0;
                    for (int i = 0; i < out2; i++) {
                        GridPane radioButtonPane = new GridPane();
                        radioButtonPane.setPadding(new Insets(10));
                        radioButtonPane.setVgap(5);
                        radioButtonPane.setHgap(5);

                        int randomCount = random.nextInt(6) + 1;

                        for (int j = 0; j < randomCount; j++) {
                            RadioButton radioButton = new RadioButton();
                            radioButtonPane.add(radioButton, j % 2, j / 2);
                        }

                        radioButtonPane.setStyle("-fx-border-color: black; -fx-border-width: 2;");

                        gridPane.add(radioButtonPane, 2 + i % 3, i / 3);

                        Label resultLabel = new Label("Kostka nr " + (i + 1) + " wynik: " + randomCount);
                        resultsBox.getChildren().add(resultLabel);

                        wynik += randomCount;
                    }

                    gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == 9);

                    Label wyniklbl = new Label("Liczba punktów to: " + wynik);
                    gridPane.add(wyniklbl, 0, 9);

                } else {
                    Alert warning = new Alert(Alert.AlertType.WARNING);
                    warning.setContentText("Podaj liczbe z przedziału 3-10");
                    warning.show();

                    System.out.println("zle");
                }
            }





        };
        wyslij.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);



        Scene scene = new Scene(gridPane, 500, 700);

        stage.setTitle("Symulacja rzutu kostka");

        stage.setScene(scene);

        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}