module com.example.decafe {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;


    opens com.example.decafe to javafx.fxml;
    exports com.example.decafe;
}