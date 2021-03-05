package dev.ranieri.services;

import dev.ranieri.daos.BookDAO;
import dev.ranieri.entities.Book;
import dev.ranieri.exceptions.InvalidUpdateException;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BookServiceImpl implements BookService{

    static Logger logger = Logger.getLogger(BookServiceImpl.class.getName());

    private BookDAO bdao;
    public BookServiceImpl(BookDAO bookDAO){
        this.bdao = bookDAO;
    }

    @Override
    public Book registerBook(Book book) {
        book.setReturnDate(Long.MAX_VALUE);
        book.setAvailable(true);
        this.bdao.createBook(book);
        return book;
    }

    @Override
    public Set<Book> getAllBooks() {
        Set<Book> books = this.bdao.getAllBooks();
        return books;
    }

    @Override
    public Set<Book> getBooksByTitle(String title) {
        Set<Book> books = this.bdao.getAllBooks();
        Set<Book> selectedBooks = new HashSet<Book>();

        for(Book b : books){
            if(b.getTitle().toLowerCase().contains(title.toLowerCase())){
                selectedBooks.add(b);
            }
        }

        return selectedBooks;
    }

    @Override
    public Book getBookById(int id) {
        Book b = this.bdao.getBookById(id);
        return b;
    }

    @Override
    public Book updateBook(Book book) {

        boolean isValid = this.conditionVerification(book);
        if(!isValid){
            throw new InvalidUpdateException("book cannot be upgraded in quality");
        }
        String status = this.statusChange(book);

        switch (status){
            case "Checked out" -> {
                logger.info("The book with id :" + book.getBookId() + " was checked out");
                Calendar calendar = Calendar.getInstance();
                long currentTime = calendar.getTimeInMillis();
                long dueDate = currentTime + TimeUnit.DAYS.toMillis(14);
                book.setReturnDate(dueDate);
            }
            case "Checked in" -> {
                logger.info("The book with id :" + book.getBookId() + " was checked in");
                book.setReturnDate(Long.MAX_VALUE);
            }
        }

        this.bdao.updateBook(book);
        return book;
    }

    @Override
    public boolean deleteBookById(int id) {
        boolean result = this.bdao.deleteBookById(id);
        return result;
    }

    private boolean conditionVerification(Book book){
        Book oldBook = this.bdao.getBookById(book.getBookId());
        if(oldBook.getCondition()>book.getCondition()){
            return  false;
        }else{
            return true;
        }

    }

    private String statusChange(Book book){
        Book oldBook = this.bdao.getBookById(book.getBookId());

        if(oldBook.getAvailable() == true && book.getAvailable() == false){
            return "Checked out";
        }
        if(oldBook.getAvailable() == false && book.getAvailable() == true){
            return "Checked in";
        }

        return "no change";

    }
}
