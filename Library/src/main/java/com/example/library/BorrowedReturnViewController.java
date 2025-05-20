package com.example.library;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class BorrowedReturnViewController {

    @FXML private TextField searchField;
    @FXML private TableView<BorrowRecord> borrowedBooksTable;
    @FXML private TableColumn<BorrowRecord, String> bookTitleColumn;
    @FXML private TableColumn<BorrowRecord, String> borrowerColumn;
    @FXML private TableColumn<BorrowRecord, String> borrowDateColumn;
    @FXML private TableColumn<BorrowRecord, String> dueDateColumn;
    @FXML private TableColumn<BorrowRecord, String> returnDateColumn;

    private ObservableList<BorrowRecord> borrowRecords;

    @FXML
    private void initialize() {
        borrowRecords = LibraryData.getInstance().getBorrowRecords();

        bookTitleColumn.setCellValueFactory(cell -> cell.getValue().getBook().titleProperty());
        borrowerColumn.setCellValueFactory(cell -> cell.getValue().borrowerNameProperty());
        borrowDateColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getBorrowDate().toString()));
        dueDateColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDueDate().toString()));
        returnDateColumn.setCellValueFactory(cell -> {
            LocalDate retDate = cell.getValue().getReturnDate();
            return new SimpleStringProperty(retDate == null ? "Not returned" : retDate.toString());
        });

        borrowedBooksTable.setItems(borrowRecords);
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String keyword = searchField.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            borrowedBooksTable.setItems(borrowRecords);
            return;
        }

        ObservableList<BorrowRecord> filtered = FXCollections.observableArrayList(
                borrowRecords.stream()
                        .filter(record -> record.getBook().getTitle().toLowerCase().contains(keyword) ||
                                record.getBorrowerName().toLowerCase().contains(keyword))
                        .collect(Collectors.toList())
        );
        borrowedBooksTable.setItems(filtered);
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        searchField.clear();
        borrowedBooksTable.setItems(borrowRecords);
    }

    @FXML
    private void handleReturnBook(ActionEvent event) {
        BorrowRecord selectedRecord = borrowedBooksTable.getSelectionModel().getSelectedItem();
        if (selectedRecord == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a record to return.");
            return;
        }

        if (selectedRecord.getReturnDate() != null) {
            showAlert(Alert.AlertType.ERROR, "Already Returned", "This book is already returned.");
            return;
        }

        selectedRecord.setReturnDate(LocalDate.now());
        selectedRecord.getBook().setStatus("Available");
        borrowedBooksTable.refresh();

        if (selectedRecord.isOverdue()) {
            long fine = selectedRecord.calculateFine();
            FineRecord fineRecord = new FineRecord(
                    selectedRecord.getBook().getTitle(),
                    selectedRecord.getBorrowerName(),
                    selectedRecord.getDueDate(),
                    selectedRecord.getReturnDate(),
                    fine,
                    "Unpaid"
            );
            LibraryData.getInstance().getFineRecords().add(fineRecord);
            showAlert(Alert.AlertType.INFORMATION, "Fine Added", "Book returned late. Fine: " + fine);
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Returned", "Book returned on time.");
        }
    }

    @FXML
    private void handleReturnToDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library/Dashboard-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
