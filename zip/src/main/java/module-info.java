module com.example.zip {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.zip to javafx.fxml;
    exports com.example.zip;
}