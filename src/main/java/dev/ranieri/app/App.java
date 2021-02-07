package dev.ranieri.app;

import dev.ranieri.controllers.BookController;
import dev.ranieri.daos.BookDAO;
import dev.ranieri.daos.LocalBookDAO;
import dev.ranieri.daos.PostgresBookDAO;
import dev.ranieri.services.BookService;
import dev.ranieri.services.BookServiceImpl;
import io.javalin.Javalin;

// Our main class. This will set up an HTTP server and the classes/objects needed to create the API
public class App {

    public static void main(String[] args) {
        Javalin app = Javalin.create(); // create a Javalin object
        // Javalin will allow us to create an HTTP server to set up pathing and responds to requests

        // create an implementation of a DAO
        BookDAO bdao = new PostgresBookDAO();
        // Create a service implementation.The book dao is 'Injected' into the the service
        // BookDAO is a dependency of the BookService
        BookService bserv = new BookServiceImpl(bdao);

        // Create a book controller injected with the dependency of the service
        BookController bookController = new BookController(bserv);

        // set up routing in Javalin
        // app.HTTPverb(route,Lambda Handler function) Javalin will invoke that function when it receives a matching request
        app.post("/books",bookController.createBook);

        app.get("/books",bookController.getAllBooks);

        app.get("/books/:id", bookController.getBookById);

        app.put("/books/:id", bookController.updateBook);

        app.delete("/books/:id", bookController.deleteBook);

        app.start();

    }
}
