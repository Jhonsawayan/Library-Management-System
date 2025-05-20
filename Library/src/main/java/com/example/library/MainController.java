package com.example.library;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import java.io.IOException;

public class MainController {

    @FXML
    private void handleGetStarted(ActionEvent event) throws IOException {
        System.out.println("Get Started button clicked. Returning to Dashboard...");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
