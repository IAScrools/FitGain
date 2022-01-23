package com.wdsm.fitgain;

import static org.junit.Assert.assertEquals;

import com.wdsm.fitgain.Entities.Book;

import org.junit.Test;

public class BookTest {

    @Test
    public void bookTest(){
        Book book = new Book("titleTest",
                "authorTest",
                "dateTest",
                "descriptionTest",
                "contentTest",
                420);
        assertEquals("titleTest", book.getTitle());
        assertEquals("authorTest", book.getAuthor());
        assertEquals("dateTest", book.getDate());
        assertEquals("descriptionTest", book.getDescription());
        System.out.println(book.getContent());
        assertEquals(420, book.getPoints());
    }

    @Test
    public void toStringPreview() {
        Book book = new Book("titleTest",
                "authorTest",
                "dateTest",
                "descriptionTest",
                "contentTest",
                420);
        System.out.println(book.toStringPreview());
    }

    @Test
    public void toStringFull() {
        Book book = new Book("titleTest",
                "authorTest",
                "dateTest",
                "descriptionTest",
                "contentTest",
                420);
        System.out.println(book.toStringFull());
    }
}