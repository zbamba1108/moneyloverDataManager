package dev.boog.moneyloverdatamanager.controllers.impl;

import dev.boog.moneyloverdatamanager.dtos.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/createMassive")
    public List<Book> createMassiveBooks() {
        List<Book> books = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Book book = new Book();
            book.setTitle("Book " + i);
            books.add(bookRepository.save(book));
        }
        return books;
    }
}
