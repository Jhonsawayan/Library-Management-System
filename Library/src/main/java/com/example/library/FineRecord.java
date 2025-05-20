package com.example.library;

import javafx.beans.property.*;
import java.time.LocalDate;

public class FineRecord {

    private final StringProperty bookTitle;
    private final StringProperty borrowerName;
    private final ObjectProperty<LocalDate> dueDate;
    private final ObjectProperty<LocalDate> returnDate;
    private final DoubleProperty fineAmount;
    private final StringProperty status;
    private final ObjectProperty<LocalDate> datePaid;

    public FineRecord(String bookTitle, String borrowerName, LocalDate dueDate,
                      LocalDate returnDate, double fineAmount, String status) {
        this.bookTitle = new SimpleStringProperty(bookTitle);
        this.borrowerName = new SimpleStringProperty(borrowerName);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
        this.returnDate = new SimpleObjectProperty<>(returnDate);
        this.fineAmount = new SimpleDoubleProperty(fineAmount);
        this.status = new SimpleStringProperty(status);
        this.datePaid = new SimpleObjectProperty<>(null);
    }

    // Property getters
    public StringProperty bookTitleProperty() { return bookTitle; }
    public StringProperty borrowerNameProperty() { return borrowerName; }
    public ObjectProperty<LocalDate> dueDateProperty() { return dueDate; }
    public ObjectProperty<LocalDate> returnDateProperty() { return returnDate; }
    public DoubleProperty fineAmountProperty() { return fineAmount; }
    public StringProperty statusProperty() { return status; }
    public ObjectProperty<LocalDate> datePaidProperty() { return datePaid; }

    // Standard getters and setters
    public String getBookTitle() { return bookTitle.get(); }
    public void setBookTitle(String value) { bookTitle.set(value); }
    public String getBorrowerName() { return borrowerName.get(); }
    public void setBorrowerName(String value) { borrowerName.set(value); }
    public LocalDate getDueDate() { return dueDate.get(); }
    public void setDueDate(LocalDate value) { dueDate.set(value); }
    public LocalDate getReturnDate() { return returnDate.get(); }
    public void setReturnDate(LocalDate value) { returnDate.set(value); }
    public double getFineAmount() { return fineAmount.get(); }
    public void setFineAmount(double value) { fineAmount.set(value); }
    public String getStatus() { return status.get(); }
    public void setStatus(String value) { status.set(value); }
    public LocalDate getDatePaid() { return datePaid.get(); }
    public void setDatePaid(LocalDate value) { datePaid.set(value); }
}
