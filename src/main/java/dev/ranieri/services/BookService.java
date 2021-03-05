package dev.ranieri.services;

import dev.ranieri.entities.Book;

import java.util.Set;

public interface BookService {

    //CREATE
    Book registerBook(Book book);

    //READ
    Set<Book> getAllBooks();
    Set<Book> getBooksByTitle(String title);
    Book getBookById(int id);

    //UPDATE
    Book updateBook(Book book);

    //DELETE
    boolean deleteBookById(int id);

}
