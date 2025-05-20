package com.example.library;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import java.io.IOException;

public class DashboardController {

    @FXML private Button Main;

    @FXML
    private void initialize() {
        System.out.println("DashboardController initialized.");
    }

    @FXML
    private void handleMainButton(ActionEvent event) throws IOException {
        System.out.println("Main button clicked.");
        switchToView("/com/example/library/Main-view.fxml", event);
    }

    @FXML
    private void handleAvailableBooksButton(ActionEvent event) throws IOException {
        System.out.println("Available Books button clicked.");
        switchToView("/com/example/library/AvailableBooks-View.fxml", event);
    }

    @FXML
    private void handleBorrowedReturnButton(ActionEvent event) throws IOException {
        System.out.println("Borrowed/Return button clicked.");
        switchToView("/com/example/library/BorrowedReturn-View.fxml", event);
    }

    @FXML
    private void handleFinesButton(ActionEvent event) throws IOException {
        System.out.println("Fines button clicked.");
        switchToView("/com/example/library/Fines-view.fxml", event);
    }

    @FXML
    private void handleLogoutButton(ActionEvent event) throws IOException {
        System.out.println("Logout button clicked.");
        switchToView("/com/example/library/Logout-view.fxml", event);
    }

    @FXML
    private void buttonHoverEnter(javafx.scene.input.MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: #d4d4d4; -fx-background-radius: 8; -fx-text-fill: #333; -fx-font-size: 14px; -fx-cursor: hand; -fx-padding: 8 16;");
    }

    @FXML
    private void buttonHoverExit(javafx.scene.input.MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: #e6e6e6; -fx-background-radius: 8; -fx-text-fill: #333; -fx-font-size: 14px; -fx-cursor: hand; -fx-padding: 8 16;");
    }

    @FXML
    private void logoutHoverEnter(javafx.scene.input.MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: #ff4c4c; -fx-text-fill: white; -fx-background-radius: 8; -fx-font-size: 14px; -fx-cursor: hand; -fx-padding: 8 16;");
    }

    @FXML
    private void logoutHoverExit(javafx.scene.input.MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-background-radius: 8; -fx-font-size: 14px; -fx-cursor: hand; -fx-padding: 8 16;");
    }

    private void switchToView(String fxmlFile, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
