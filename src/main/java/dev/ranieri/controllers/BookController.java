package dev.ranieri.controllers;

import com.google.gson.Gson;
import dev.ranieri.entities.Book;
import dev.ranieri.exceptions.InvalidUpdateException;
import dev.ranieri.services.BookService;
import io.javalin.http.Handler;

import java.util.Set;

// A controller class is responsible for receiving and responding to HTTP requests
// All the logic in this class should be based around that principle
// Tasks normally include, reading path parameters, query parameters, converting objects to and from JSON format
// Controllers will use a service(s) to perform more complicated business logic
public class BookController {

    //Dependencies
    private Gson gson = new Gson(); // gson is a a Simple object that will allow us to convert to and from JSON
    private BookService bserv;

    //we create an instance of the controller by injecting it with a book service
    public BookController(BookService bookService){
        this.bserv = bookService;
    }

    public Handler createBook = (ctx) -> {
        Book book = this.gson.fromJson(ctx.body(), Book.class); // convert the JSON body into a Java Object
        book = this.bserv.registerBook(book);
        String bookJSON = gson.toJson(book);
        ctx.status(201); // creating a new resource is a 201 status code
        ctx.result(bookJSON);
    };

    public Handler getAllBooks = (ctx) ->{
        String title = ctx.queryParam("title","none"); // get the query param /books?title=adventure
        // second parameter is a default value if no query param found

        if(title.equals("none")){
            Set<Book> books = this.bserv.getAllBooks();
            String booksJSON = this.gson.toJson(books);
            ctx.result(booksJSON);
        }else{
            Set<Book> books = this.bserv.getBooksByTitle(title);
            String booksJSON = this.gson.toJson(books);
            ctx.result(booksJSON);
        }


    };

    public Handler getBookById = (ctx) ->{
        int id = Integer.parseInt(ctx.pathParam("id")); // get a path parameter books/54 All params are read as strings by default
        Book book = this.bserv.getBookById(id);
        String bookJSON = this.gson.toJson(book);
        ctx.result(bookJSON);
    };

    public Handler updateBook = (ctx) ->{
        int id = Integer.parseInt(ctx.pathParam("id"));
        Book book = this.gson.fromJson(ctx.body(), Book.class);
        book.setBookId(id);
        try{
            this.bserv.updateBook(book);
            String bookJSON = this.gson.toJson(book);
            ctx.result(bookJSON);
        }catch (InvalidUpdateException e){
            ctx.status(400); // 400 for bad request
            ctx.result("Books cannot be upgraded in quality");
        }

    };
    public Handler deleteBook = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean result = this.bserv.deleteBookById(id);
        if(result){
            ctx.status(200);
        }else{
            ctx.status(404); // resource could not be found
        }
    };

}
