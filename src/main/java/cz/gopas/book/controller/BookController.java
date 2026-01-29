package cz.gopas.book.controller;

import cz.gopas.book.bean.Book;
import cz.gopas.book.bean.BookDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface BookController {

    @GetMapping("/books")
    public List<BookDTO> getBooks(@RequestParam(value = "title", required = false) String title);

    @GetMapping("/books/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable("id") Long id);

    @PostMapping("/books")
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO newBook);

    @PutMapping("/books/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") Long id, @RequestBody BookDTO updatedBook);

    @DeleteMapping("/books/{id}")
    public ResponseEntity<BookDTO> deleteBook(@PathVariable("id") Long id);

}
