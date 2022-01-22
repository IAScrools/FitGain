package com.wdsm.fitgain.Entities;

import com.google.firebase.database.PropertyName;

public class Book {

    @PropertyName("Title")
    private String Title;

    @PropertyName("Author")
    private String Author;

    @PropertyName("Date")
    private String Date;

    @PropertyName("Description")
    private String Description;

    @PropertyName("Content")
    private String Content;

    @PropertyName("Points")
    private int Points;

    public int getPoints() {
        return Points;
    }


    public String getDescription() {
        return Description;
    }

    public Book(String title, String author, String date, String description, String content, int points) {
        Title = title;
        Author = author;
        Date = date;
        Description = description;
        Content = content;
        Points = points;
    }

    public Book() {
    }

    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return Author;
    }

    public String getDate() {
        return Date;
    }

    public String getContent() {
        String newLine = System.getProperty("line.separator");
        return getTitle() + newLine + newLine + Content;
    }

    public String toStringPreview()
    {
        String newLine = System.getProperty("line.separator");
        return getTitle() + newLine
                + getAuthor()
                + " (" + getDate() + ")"
                ;
    }

    public String toStringFull()
    {
        String newLine = System.getProperty("line.separator");
        return getTitle() + newLine
                + getAuthor()
                + " (" + getDate() + ")"
                + newLine + newLine + getDescription() + newLine + newLine
                + "Punkty potrzebne do odblokowania: " + getPoints();
    }

}
