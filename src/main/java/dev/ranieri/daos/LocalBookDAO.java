package dev.ranieri.daos;

import dev.ranieri.entities.Book;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LocalBookDAO implements  BookDAO{

    private final Map<Integer,Book> book_table = new HashMap<Integer,Book>();
    private int idCounter = 0;


    @Override
    public Book createBook(Book book) {
        book.setBookId(++this.idCounter);
        book_table.put(this.idCounter, book);
        return book;
    }

    @Override
    public Set<Book> getAllBooks() {
        Set<Book> books = new HashSet<Book>(this.book_table.values());
        return books;
    }

    @Override
    public Book getBookById(int id) {
        Book book = this.book_table.get(id);
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        this.book_table.put(book.getBookId(),book);
        return book;
    }

    @Override
    public boolean deleteBookById(int id) {
        Book book = book_table.remove(id);
        return book != null;
    }

}
