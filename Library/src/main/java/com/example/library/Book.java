package com.example.library;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {
    private final StringProperty title;
    private final StringProperty author;
    private final StringProperty year;
    private final StringProperty status;

    public Book(String title, String author, String year, String status) {
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.year = new SimpleStringProperty(year);
        this.status = new SimpleStringProperty(status);
    }

    // Property getters for JavaFX binding
    public StringProperty titleProperty() { return title; }
    public StringProperty authorProperty() { return author; }
    public StringProperty yearProperty() { return year; }
    public StringProperty statusProperty() { return status; }

    // Basic getters
    public String getTitle() { return title.get(); }
    public String getAuthor() { return author.get(); }
    public String getYear() { return year.get(); }
    public String getStatus() { return status.get(); }

    // Setters
    public void setTitle(String title) { this.title.set(title); }
    public void setAuthor(String author) { this.author.set(author); }
    public void setYear(String year) { this.year.set(year); }
    public void setStatus(String status) { this.status.set(status); }
}
