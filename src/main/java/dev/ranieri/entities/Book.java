package dev.ranieri.entities;

// An entity is is a class designed to hold information
// they should have very minimal if any logic
// the fields in an entity generally should match 1-1 to columns in a SQL table
// Should be a Java Bean
public class Book {

    // an entity with an ID of 0 is understood to be unsaved
    private int bookId; // primary key
    private String title;
    private String author;
    private int condition;
    private boolean available;
    private long returnDate;

    public Book() {
    }

    public Book(int bookId, String title, String author, int condition, boolean available, long returnDate) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.condition = condition;
        this.available = available;
        this.returnDate = returnDate;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }


    public long getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(long returnDate) {
        this.returnDate = returnDate;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", condition=" + condition +
                ", available=" + available +
                ", returnDate=" + returnDate +
                '}';
    }
}
