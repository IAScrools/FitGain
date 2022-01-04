package com.wdsm.fitgain.Entities;

import java.util.List;

public class Book {
    private String Title;
    private String Author;
    private String Date;
    private List Content;

    public Book(String title, String author, String date, List content) {
        Title = title;
        Author = author;
        Date = date;
        Content = content;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public List getContent() {
        return Content;
    }

    public void setContent(List content) {
        Content = content;
    }
}
