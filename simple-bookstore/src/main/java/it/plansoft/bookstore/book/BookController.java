package it.plansoft.bookstore.book;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class BookController {

    private final BookRepository books;

    public BookController(BookRepository books) {
        this.books = books;
    }

    @GetMapping(path = "/books", produces = "application/json")
    public Iterable<Book> getAll() {
        return books.findAll();
    }

    @PostMapping(path = "/books", consumes = "application/json")
    public void save(Book book) {
        books.save(book);
    }

    @GetMapping(path = "/books/{id}", produces = "application/json")
    public Book getByID(@PathVariable("id") Integer id) {
        return books.findById(id).orElseThrow(RuntimeException::new);
    }
}
