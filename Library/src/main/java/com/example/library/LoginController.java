package com.example.library;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import javafx.geometry.Insets;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (UserData.isValid(username, password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library/main-view.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Library Dashboard");
            } catch (Exception e) {
                errorLabel.setText("Failed to load main view.");
                e.printStackTrace();
            }
        } else {
            errorLabel.setStyle("-fx-text-fill: red;");
            errorLabel.setText("Invalid username or password.");
        }
    }

    @FXML
    private void handleRegister() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Register New User");
        dialog.setHeaderText("Enter a new username:");
        dialog.setContentText("Username:");
        dialog.showAndWait().ifPresent(username -> {
            TextInputDialog passDialog = new TextInputDialog();
            passDialog.setTitle("Set Password");
            passDialog.setHeaderText("Enter a password for " + username);
            passDialog.setContentText("Password:");
            passDialog.showAndWait().ifPresent(password -> {
                boolean added = UserData.addUser(username, password);
                if (added) {
                    errorLabel.setStyle("-fx-text-fill: green;");
                    errorLabel.setText("User registered successfully!");
                } else {
                    errorLabel.setStyle("-fx-text-fill: red;");
                    errorLabel.setText("Username already exists.");
                }
            });
        });
    }

    @FXML
    private void handleChangePassword() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Change Password");
        dialog.setHeaderText("Enter your username and current password");

        GridPane grid = new GridPane();
        TextField username = new TextField();
        PasswordField oldPass = new PasswordField();
        PasswordField newPass = new PasswordField();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Old Password:"), 0, 1);
        grid.add(oldPass, 1, 1);
        grid.add(new Label("New Password:"), 0, 2);
        grid.add(newPass, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Pair<>(oldPass.getText(), newPass.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            boolean changed = UserData.changePassword(username.getText(), result.getKey(), result.getValue());
            if (changed) {
                errorLabel.setStyle("-fx-text-fill: green;");
                errorLabel.setText("Password updated successfully!");
            } else {
                errorLabel.setStyle("-fx-text-fill: red;");
                errorLabel.setText("Invalid username or password.");
            }
        });
    }
}
