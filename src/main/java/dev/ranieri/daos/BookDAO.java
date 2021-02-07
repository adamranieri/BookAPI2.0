package dev.ranieri.daos;

import dev.ranieri.entities.Book;

import java.util.Set;

public interface BookDAO {

    //CREATE
    Book createBook(Book book);

    //READ
    Set<Book> getAllBooks();
    Book getBookById(int id);

    //UPDATE
    Book updateBook(Book book);

    //DELETE
    boolean deleteBookById(int id);
}
