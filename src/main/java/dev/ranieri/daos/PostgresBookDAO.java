package dev.ranieri.daos;

import dev.ranieri.entities.Book;
import dev.ranieri.services.BookServiceImpl;
import dev.ranieri.utils.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class PostgresBookDAO implements BookDAO{

    static Logger logger = Logger.getLogger(PostgresBookDAO.class.getName());

    @Override
    public Book createBook(Book book) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "insert into book (title,author,book_condition,available,return_date) values (?,?,?,?,?) ";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,book.getTitle());
            ps.setString(2,book.getAuthor());
            ps.setInt(3,book.getCondition());
            ps.setBoolean(4,book.getAvailable());
            ps.setLong(5,book.getReturnDate());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int key = rs.getInt("book_id");
            book.setBookId(key);

            return book;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("could not create book",sqlException);
        }
        return null;
    }

    @Override
    public Set<Book> getAllBooks() {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from book ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            Set<Book> books = new HashSet<Book>();

            while(rs.next()){
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setCondition(rs.getInt("book_condition"));
                book.setAvailable(rs.getBoolean("available"));
                book.setReturnDate(rs.getLong("return_date"));
                books.add(book);
            }

            return books;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("could not retrieve book",sqlException);
            return null;
        }
    }

    @Override
    public Book getBookById(int id) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from book where book_id = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            rs.next();

            Book book = new Book();
            book.setBookId(rs.getInt("book_id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setCondition(rs.getInt("book_condition"));
            book.setAvailable(rs.getBoolean("available"));
            book.setReturnDate(rs.getLong("return_date"));

            return book;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("could not retrieve book",sqlException);
            return null;
        }
    }

    @Override
    public Book updateBook(Book book) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "update book set title = ?, author = ? ,book_condition = ?, available = ?,return_date = ? where book_id =? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,book.getTitle());
            ps.setString(2,book.getAuthor());
            ps.setInt(3,book.getCondition());
            ps.setBoolean(4,book.getAvailable());
            ps.setLong(5,book.getReturnDate());
            ps.setInt(6,book.getBookId());
            ps.executeUpdate();

            // SQL INJECTION VERSION
            // DO NOT USE
            // 1. Looks terrible
            // 2. Leaves you open databaase open to malicious attacks
//            String injection = "update book set title = " + "'" +book.getTitle()+"'"  +
//                    ", author = " + "'" + book.getAuthor() + "'"  +
//                    ", book_condition = " +book.getCondition() +
//                    ", available = " + book.getAvailable() +
//                    ", return_date = " + book.getReturnDate() +
//                    " where book_id = " + book.getBookId();
//            System.out.println(injection);
//            Statement statement = conn.createStatement();
//            statement.execute(injection);
            //

            return book;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("could not update book",sqlException);
            return null;
        }

    }

    @Override
    public boolean deleteBookById(int id) {

        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "delete from book  where book_id =? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.execute();

            return true;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("could not delete book",sqlException);
            return false;
        }


    }
}
