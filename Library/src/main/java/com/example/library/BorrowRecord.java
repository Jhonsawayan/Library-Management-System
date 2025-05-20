package com.example.library;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BorrowRecord {

    private final Book book;
    private final SimpleStringProperty borrowerName;
    private final SimpleObjectProperty<LocalDate> borrowDate;
    private final SimpleObjectProperty<LocalDate> dueDate;
    private final SimpleObjectProperty<LocalDate> returnDate;

    public BorrowRecord(Book book, String borrowerName, LocalDate borrowDate, LocalDate dueDate) {
        this.book = book;
        this.borrowerName = new SimpleStringProperty(borrowerName);
        this.borrowDate = new SimpleObjectProperty<>(borrowDate);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
        this.returnDate = new SimpleObjectProperty<>(null);
    }

    public Book getBook() {
        return book;
    }

    public String getBorrowerName() {
        return borrowerName.get();
    }

    public SimpleStringProperty borrowerNameProperty() {
        return borrowerName;
    }

    public LocalDate getBorrowDate() {
        return borrowDate.get();
    }

    public SimpleObjectProperty<LocalDate> borrowDateProperty() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate.get();
    }

    public SimpleObjectProperty<LocalDate> dueDateProperty() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate.get();
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate.set(returnDate);
    }

    public SimpleObjectProperty<LocalDate> returnDateProperty() {
        return returnDate;
    }

    public boolean isOverdue() {
        if (returnDate.get() == null) return false;
        return returnDate.get().isAfter(dueDate.get());
    }

    public long calculateFine() {
        if (!isOverdue()) return 0;
        return ChronoUnit.DAYS.between(dueDate.get(), returnDate.get()) * 10; // e.g., 10 units per day
    }
}
