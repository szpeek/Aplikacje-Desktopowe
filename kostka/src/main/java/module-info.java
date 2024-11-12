module com.example.kostka {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kostka to javafx.fxml;
    exports com.example.kostka;
}