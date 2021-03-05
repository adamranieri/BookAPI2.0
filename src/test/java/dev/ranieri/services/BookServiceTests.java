package dev.ranieri.services;

import dev.ranieri.daos.BookDAO;
import dev.ranieri.entities.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {

    @Mock
    BookDAO mockBookDAO = null;
    BookService bookService = null;

    @BeforeEach
    void bookDaoSetUp(){
        Book greatGatsby = new Book(1,"The Great Gatsby","F. Scott Fitzgerald",1,true,0);
        Book tomSawyer = new Book(2,"The Adventures of Tom Sawyer","Mark Twain",1,true,0);
        Book theHobbit = new Book(3,"The Hobbit","J.R. Tolkien",1,true,0);
        Book chamberOfSecrets = new Book(4,"Harry Potter and the Chamber of Secrets","J.K. Rowling",1,true,0);
        Book deathlyHallows = new Book(5,"Harry Potter and the Deathly Hallows","J.K. Rowling",1,true,0);
        Set<Book> allBooks = new HashSet<Book>();
        allBooks.add(greatGatsby);
        allBooks.add(tomSawyer);
        allBooks.add(theHobbit);
        allBooks.add(chamberOfSecrets);
        allBooks.add(deathlyHallows);
        Mockito.when(mockBookDAO.getAllBooks()).thenReturn(allBooks);
        bookService = new BookServiceImpl(mockBookDAO);
    }

    @Test
    void book_titles_containing_search(){
        Set<Book> books = this.bookService.getBooksByTitle("harry");
        Assertions.assertEquals(2,books.size());
    }







}
