package com.example.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class  Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/library/Login.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setTitle("Library Login");

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true); // ⬅️ This line makes it full screen
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
