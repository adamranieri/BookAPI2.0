package dev.ranieri.daos;

import dev.ranieri.entities.Book;

import java.util.Set;

// A DAO (Data Access Object ) is a class designed to perform CRUD operations on a single entity
// They should NOT contain complex logic.
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
