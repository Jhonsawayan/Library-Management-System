package com.example.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LibraryData {

    private static LibraryData instance = null;

    private ObservableList<Book> bookList = FXCollections.observableArrayList();
    private ObservableList<BorrowRecord> borrowRecords = FXCollections.observableArrayList();
    private ObservableList<FineRecord> fineRecords = FXCollections.observableArrayList();

    private LibraryData() {
        // Sample books initialization
        bookList.addAll(
                new Book("The Great Gatsby", "F. Scott Fitzgerald", "1925", "Available"),
                new Book("1984", "George Orwell", "1949", "Borrowed"),
                new Book("Pride and Prejudice", "Jane Austen", "1813", "Available"),
                new Book("The Great Gatsby", "F. Scott Fitzgerald", "1925", "Available"),
                new Book("Moby-Dick", "Herman Melville", "1851", "Available"),
                new Book("The Catcher in the Rye", "J.D. Salinger", "1951", "Available"),
                new Book("The Hobbit", "J.R.R. Tolkien", "1937", "Available"),
                new Book("War and Peace", "Leo Tolstoy", "1869", "Available"),
                new Book("Jane Eyre", "Charlotte Brontë", "1847", "Available"),
                new Book("The Adventures of Huckleberry Finn", "Mark Twain", "1884", "Available"),
                new Book("Crime and Punishment", "Fyodor Dostoevsky", "1866", "Available"),
                new Book("The Brothers Karamazov", "Fyodor Dostoevsky", "1880", "Available"),
                new Book("Brave New World", "Aldous Huxley", "1932", "Available"),
                new Book("Wuthering Heights", "Emily Brontë", "1847", "Available"),
                new Book("Les Misérables", "Victor Hugo", "1862", "Available")
        );
    }

    public static LibraryData getInstance() {
        if (instance == null) {
            instance = new LibraryData();
        }
        return instance;
    }

    public ObservableList<Book> getBookList() {
        return bookList;
    }

    public ObservableList<BorrowRecord> getBorrowRecords() {
        return borrowRecords;
    }

    public ObservableList<FineRecord> getFineRecords() {
        return fineRecords;
    }
}
