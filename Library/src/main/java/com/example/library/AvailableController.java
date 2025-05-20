package com.example.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class AvailableController {

    @FXML private TextField searchField;
    @FXML private TableView<Book> booksTable;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> yearColumn;
    @FXML private TableColumn<Book, String> statusColumn;
    @FXML private Label availableCountLabel;

    private ObservableList<Book> bookList;

    @FXML
    private void initialize() {
        // Get shared book list from LibraryData singleton
        bookList = LibraryData.getInstance().getBookList();

        // Set up columns
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        booksTable.setItems(bookList);
        updateAvailableBooksCount();
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String keyword = searchField.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            booksTable.setItems(bookList);
            updateAvailableBooksCount();
            return;
        }

        ObservableList<Book> filteredList = FXCollections.observableArrayList();
        for (Book book : bookList) {
            if (book.getTitle().toLowerCase().contains(keyword) ||
                    book.getAuthor().toLowerCase().contains(keyword) ||
                    book.getYear().toLowerCase().contains(keyword)) {
                filteredList.add(book);
            }
        }
        booksTable.setItems(filteredList);
        updateAvailableBooksCount(filteredList);
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        searchField.clear();
        booksTable.setItems(bookList);
        updateAvailableBooksCount();
    }

    @FXML
    private void handleReturnToDashboard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Dashboard-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleBorrowBook(ActionEvent event) {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null && "Available".equals(selectedBook.getStatus())) {

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Borrow Book");
            dialog.setHeaderText("Enter Borrower's Name");
            dialog.setContentText("Name:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String borrowerName = result.get().trim();

                if (borrowerName.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Invalid Input", "Borrower's name cannot be empty.");
                    return;
                }

                selectedBook.setStatus("Borrowed");
                booksTable.refresh();
                updateAvailableBooksCount();

                LocalDate borrowDate = LocalDate.now();
                LocalDate dueDate = borrowDate.plusWeeks(2);

                BorrowRecord newRecord = new BorrowRecord(selectedBook, borrowerName, borrowDate, dueDate);
                LibraryData.getInstance().getBorrowRecords().add(newRecord);

                showAlert(Alert.AlertType.INFORMATION, "Success", "You borrowed: " + selectedBook.getTitle());
            }

        } else {
            showAlert(Alert.AlertType.WARNING, "Borrow Error", "Please select an available book to borrow.");
        }
    }
    @FXML
    private void handleDeleteBook(ActionEvent event) {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            showAlert(Alert.AlertType.WARNING, "Delete Book", "Please select a book to delete.");
            return;
        }

        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Delete Book");
        confirmDialog.setHeaderText("Are you sure you want to delete this book?");
        confirmDialog.setContentText("Title: " + selectedBook.getTitle());

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            bookList.remove(selectedBook);
            booksTable.refresh();
            updateAvailableBooksCount();
            showAlert(Alert.AlertType.INFORMATION, "Book Deleted", "Book successfully deleted.");
        }
    }

    @FXML
    private void handleReturnBook(ActionEvent event) {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null && "Borrowed".equals(selectedBook.getStatus())) {
            selectedBook.setStatus("Available");
            booksTable.refresh();
            updateAvailableBooksCount();

            ObservableList<BorrowRecord> borrowRecords = LibraryData.getInstance().getBorrowRecords();
            borrowRecords.removeIf(record -> record.getBook().equals(selectedBook) && record.getReturnDate() == null);

            showAlert(Alert.AlertType.INFORMATION, "Success", "You returned: " + selectedBook.getTitle());
        } else {
            showAlert(Alert.AlertType.WARNING, "Return Error", "Please select a borrowed book to return.");
        }
    }

    @FXML
    private void handleAddBook(ActionEvent event) {
        Dialog<Book> dialog = new Dialog<>();
        dialog.setTitle("Add New Book");
        dialog.setHeaderText("Enter book details");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        TextField yearField = new TextField();
        yearField.setPromptText("Year");

        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Author:"), 0, 1);
        grid.add(authorField, 1, 1);
        grid.add(new Label("Year:"), 0, 2);
        grid.add(yearField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Book(
                        titleField.getText().trim(),
                        authorField.getText().trim(),
                        yearField.getText().trim(),
                        "Available"
                );
            }
            return null;
        });

        Optional<Book> result = dialog.showAndWait();

        result.ifPresent(book -> {
            if (book.getTitle().isEmpty() || book.getAuthor().isEmpty() || book.getYear().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Invalid Input", "All fields must be filled.");
                return;
            }

            bookList.add(book);
            booksTable.refresh();
            updateAvailableBooksCount();
            showAlert(Alert.AlertType.INFORMATION, "Book Added", "Book added: " + book.getTitle());
        });
    }

    private void updateAvailableBooksCount() {
        long count = bookList.stream()
                .filter(book -> "Available".equals(book.getStatus()))
                .count();
        availableCountLabel.setText("Available Books: " + count);
    }

    private void updateAvailableBooksCount(ObservableList<Book> filteredList) {
        long count = filteredList.stream()
                .filter(book -> "Available".equals(book.getStatus()))
                .count();
        availableCountLabel.setText("Available Books: " + count);
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
