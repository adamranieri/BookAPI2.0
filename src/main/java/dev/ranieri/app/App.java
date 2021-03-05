package dev.ranieri.app;

import dev.ranieri.controllers.BookController;
import dev.ranieri.daos.BookDAO;
import dev.ranieri.daos.LocalBookDAO;
import dev.ranieri.daos.PostgresBookDAO;
import dev.ranieri.services.BookService;
import dev.ranieri.services.BookServiceImpl;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        BookDAO bdao = new PostgresBookDAO();
        BookService bserv = new BookServiceImpl(bdao);
        BookController bookController = new BookController(bserv);

        app.post("/books",bookController.createBook);

        app.get("/books",bookController.getAllBooks);

        app.get("/books/:id", bookController.getBookById);

        app.put("/books/:id", bookController.updateBook);

        app.delete("/books/:id", bookController.deleteBook);

        app.start();

    }
}
