module com.example.fileform {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fileform to javafx.fxml;
    exports com.example.fileform;
}