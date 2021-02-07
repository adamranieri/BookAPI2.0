package dev.ranieri.daotests;

import dev.ranieri.daos.BookDAO;
import dev.ranieri.daos.LocalBookDAO;
import dev.ranieri.daos.PostgresBookDAO;
import dev.ranieri.entities.Book;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookDaoTests {

    private static BookDAO bdao = new PostgresBookDAO();
    static Logger logger = Logger.getLogger(BookDaoTests.class.getName());

    private static Book testBook = null;
    // a place holder book to be used in my tests
    // These tests need to be run in order for consistency sake

    @Test
    @Order(1)
    void create_book(){
        Book hp = new Book(0,"Harry Potter and the Chamber of Secrets","J.K. Rowling",1,true,Long.MAX_VALUE);
        bdao.createBook(hp);
        BookDaoTests.testBook = hp;
        System.out.println(hp);
        Assertions.assertNotEquals(0,hp.getBookId());
    }

    @Order(2)
    @Test
    void get_book(){
        Book hp = bdao.getBookById(testBook.getBookId());
        Assertions.assertEquals("Harry Potter and the Chamber of Secrets",hp.getTitle());
    }

    @Order(3)
    @Test
    void update_book(){
        Book hp = bdao.getBookById(testBook.getBookId());
        hp.setCondition(3);
        bdao.updateBook(hp);
        Book updatedBook = bdao.getBookById(hp.getBookId());
        Assertions.assertEquals(3,updatedBook.getCondition());
    }

    @Order(4)
    @Test
    void delete_book(){
        Assertions.assertTrue(bdao.deleteBookById(testBook.getBookId()));
    }

    @Test
    @Disabled
    void sql_injection(){
        Book evilBook = new Book();
        evilBook.setBookId(1);
        evilBook.setTitle("Mwahahaha");
        evilBook.setAuthor("u a scrub' where book_id = 1 or 1=1; -- "); // or 1=1 is ALWAYS TRUE APPLIES TO EVERY RECORD
        evilBook.setCondition(1);
        evilBook.setReturnDate(0);
        evilBook.setReturnDate(0);
        bdao.updateBook(evilBook);
    }


}
