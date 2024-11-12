package com.example.kostka;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.event.EventHandler;

public class kostka extends Application {
    @Override
    public void start(Stage stage) {
        //creating label email
        Label lbl1 = new Label("Podaj liczbe z przedziału 3-10");
        TextField text1 = new TextField();
        Button wyslij = new Button("Wyslij");



        //Creating a Grid Pane
        GridPane gridPane = new GridPane();


        gridPane.add(lbl1,0,0);
        gridPane.add(text1, 0,1);
        gridPane.add(wyslij,0,2);

        //Setting size for the pane
        gridPane.setMinSize(500, 700);


        Group groupKostka = new Group();






        gridPane.getChildren().add(groupKostka);

        //Setting the padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String out1 = new String(text1.getText());
                Integer out2 = Integer.parseInt(out1);
                if(out2 <= 10 && out2 >= 3){
                    gridPane.getChildren().removeIf(node -> GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) >= 2);
                    for(int i = 0; i < out2; i++){
                        GridPane radioButtonPane = new GridPane();
                        radioButtonPane.setPadding(new Insets(10));
                        radioButtonPane.setVgap(5);
                        radioButtonPane.setHgap(5);

                        RadioButton btn1 = new RadioButton();
                        RadioButton btn2 = new RadioButton();
                        RadioButton btn3 = new RadioButton();
                        RadioButton btn4 = new RadioButton();
                        RadioButton btn5 = new RadioButton();
                        RadioButton btn6 = new RadioButton();

                        radioButtonPane.add(btn1, 0, 0);
                        radioButtonPane.add(btn2, 0, 1);
                        radioButtonPane.add(btn3, 0, 2);
                        radioButtonPane.add(btn4, 1, 0);
                        radioButtonPane.add(btn5, 1, 1);
                        radioButtonPane.add(btn6, 1, 2);

                        radioButtonPane.setStyle("-fx-border-color: black; -fx-border-width: 2;");

                        gridPane.add(radioButtonPane, 2 + i % 3, i / 3);
                    }




                }else {
                    System.out.println("zle");
                }
            }
        };
        wyslij.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);


        //Creating a scene object
        Scene scene = new Scene(gridPane, 500, 700);

        //Setting title to the Stage
        stage.setTitle("Symulacja rzutu kostka");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}