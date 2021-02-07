package dev.ranieri.controllers;

import com.google.gson.Gson;
import dev.ranieri.entities.Book;
import dev.ranieri.exceptions.InvalidUpdateException;
import dev.ranieri.services.BookService;
import io.javalin.http.Handler;

import java.util.Set;

public class BookController {

    private Gson gson = new Gson();
    private BookService bserv;

    public BookController(BookService bookService){
        this.bserv = bookService;
    }

    public Handler createBook = (ctx) -> {
        Book book = this.gson.fromJson(ctx.body(), Book.class);
        book = this.bserv.registerBook(book);
        String bookJSON = gson.toJson(book);
        ctx.status(201);
        ctx.result(bookJSON);
    };

    public Handler getAllBooks = (ctx) ->{
        String title = ctx.queryParam("title","none");

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
        int id = Integer.parseInt(ctx.pathParam("id"));
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
            ctx.status(400);
            ctx.result("Books cannot be upgraded in quality");
        }

    };
    public Handler deleteBook = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean result = this.bserv.deleteBookById(id);
        if(result){
            ctx.status(200);
        }else{
            ctx.status(404);
        }
    };

}
