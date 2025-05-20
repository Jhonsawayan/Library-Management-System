package com.example.library;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class FinesViewController {

    @FXML private TextField searchField;
    @FXML private TextField bookTitleField;
    @FXML private TextField borrowerNameField;
    @FXML private DatePicker dueDatePicker;
    @FXML private DatePicker returnDatePicker;
    @FXML private TextField fineAmountField;
    @FXML private DatePicker datePaidPicker;
    @FXML private ProgressBar progressBar;
    @FXML private Button payFineButton;

    @FXML private TableView<FineRecord> finesTable;
    @FXML private TableColumn<FineRecord, String> bookColumn;
    @FXML private TableColumn<FineRecord, String> borrowerColumn;
    @FXML private TableColumn<FineRecord, LocalDate> dueDateColumn;
    @FXML private TableColumn<FineRecord, LocalDate> returnDateColumn;
    @FXML private TableColumn<FineRecord, Double> fineColumn;
    @FXML private TableColumn<FineRecord, String> statusColumn;

    private ObservableList<FineRecord> finesList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        bookColumn.setCellValueFactory(data -> data.getValue().bookTitleProperty());
        borrowerColumn.setCellValueFactory(data -> data.getValue().borrowerNameProperty());
        dueDateColumn.setCellValueFactory(data -> data.getValue().dueDateProperty());
        returnDateColumn.setCellValueFactory(data -> data.getValue().returnDateProperty());
        fineColumn.setCellValueFactory(data -> data.getValue().fineAmountProperty().asObject());
        statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());

        loadFines();
        finesTable.setItems(finesList);
        progressBar.setProgress(0);

        finesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillForm(newSelection);
            }
        });
    }

    private void loadFines() {
        finesList.clear();
        finesList.addAll(
                new FineRecord("Java Programming", "Alice Smith", LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), 50.0, "Unpaid"),
                new FineRecord("Database Systems", "Bob Johnson", LocalDate.now().minusDays(15), LocalDate.now().minusDays(3), 75.0, "Paid"),
                new FineRecord("Networking Basics", "Carol Lee", LocalDate.now().minusDays(7), LocalDate.now().minusDays(1), 30.0, "Unpaid")
        );
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase().trim();
        if (keyword.isEmpty()) {
            finesTable.setItems(finesList);
        } else {
            ObservableList<FineRecord> filtered = finesList.stream()
                    .filter(fine -> fine.getBookTitle().toLowerCase().contains(keyword)
                            || fine.getBorrowerName().toLowerCase().contains(keyword))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            finesTable.setItems(filtered);
        }
    }

    @FXML
    private void handleRefresh() {
        searchField.clear();
        loadFines();
        finesTable.setItems(finesList);
    }

    @FXML
    private void handlePayFine() {
        FineRecord selected = finesTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a fine record to pay.");
            return;
        }

        if ("Paid".equalsIgnoreCase(selected.getStatus())) {
            showAlert(Alert.AlertType.INFORMATION, "Already Paid", "This fine is already marked as paid.");
            return;
        }

        if (datePaidPicker.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please select the payment date.");
            return;
        }

        payFineButton.setDisable(true);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> progressBar.setProgress(0)),
                new KeyFrame(Duration.seconds(2), e -> {
                    progressBar.setProgress(1);
                    selected.setStatus("Paid");
                    selected.setDatePaid(datePaidPicker.getValue());
                    finesTable.refresh();

                    showAlert(Alert.AlertType.INFORMATION, "Payment Successful", "The fine payment has been processed.");
                    payFineButton.setDisable(false);
                })
        );
        timeline.play();
    }

    private void fillForm(FineRecord fine) {
        bookTitleField.setText(fine.getBookTitle());
        borrowerNameField.setText(fine.getBorrowerName());
        dueDatePicker.setValue(fine.getDueDate());
        returnDatePicker.setValue(fine.getReturnDate());
        fineAmountField.setText(String.valueOf(fine.getFineAmount()));
        datePaidPicker.setValue(fine.getDatePaid());
    }

    @FXML
    private void handleReturn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library/Dashboard-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) payFineButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to return to the Dashboard.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
