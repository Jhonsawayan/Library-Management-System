package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class LogoutViewController {

    @FXML
    private void handleLogout(ActionEvent event) {
        System.out.println("Logout action performed.");
        // Add logout logic here (e.g., clear session, exit, or go back to login screen)
        System.exit(0);  // Closes the application
    }

    @FXML
    private void handleReturnToDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library/Dashboard-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
