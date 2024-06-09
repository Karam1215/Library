package ru.karam.project.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {
    private int book_id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 4, max = 100, message = "Длина ФИО должна быть от 5 до 50 символов")
    private String title;
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 4, max = 100, message = "Длина ФИО должна быть от 5 до 50 символов")
    private String author;

    @Min(value = 1900, message = "Год рождения должен быть больше 1900")
    private int year;


    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public Book() {}

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
