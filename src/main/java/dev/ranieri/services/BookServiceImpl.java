package dev.ranieri.services;

import dev.ranieri.daos.BookDAO;
import dev.ranieri.entities.Book;
import dev.ranieri.exceptions.InvalidUpdateException;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

// The bulk of business logic belongs in service classes
// Business logic is the implementation of user requirements applicable to a specific application
// examples in this application Search By title, Logging when a book is checked in/out, setting a return date, condition validation
// A service might also have methods that wrap around a DAO class and are fairly lean in business logic
// examples get book by id, get all books, delete books
public class BookServiceImpl implements BookService{

    static Logger logger = Logger.getLogger(BookServiceImpl.class.getName());

    // A service might get created with different DAOs
    //  we might use a local one or different versions of SQL
    // By constructing our class with the DAO as a dependency we create an abstract loose coupling
    // makes our code easier to scale and more modular
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

    @Override //for our get books by title the client requested that the search be case insensitive and just contain the search word
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

    // private helper functions
    // another class has no reason to use these directly
    // we want to enforce that a service is interacted with only using the public interface methods
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
