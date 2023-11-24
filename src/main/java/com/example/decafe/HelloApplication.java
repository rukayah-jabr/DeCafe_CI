package com.example.decafe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

// Class used to start the JavaFX Application
public class HelloApplication extends Application {

    public static Stage stage;
    @Override
    // Start Application by starting the Stage
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("startScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage = stage;
        stage.getIcons().add(new Image("file:src/main/resources/com/example/decafe/mugTabPic.png"));
        stage.setTitle("DeCafé");
        HelloApplication.stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}