package cz.gopas.book.controller;

import cz.gopas.book.bean.Book;
import cz.gopas.book.bean.BookDTO;
import cz.gopas.book.persistence.BookStorage;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class BookControllerV1 implements BookController {

    @Autowired
    private BookStorage storage;

    @Value("${custom.book-service-header-name}")
    private String headerName;

//    @Autowired
//    public BookControllerV1(BookStorage storage, @Value("${custom.book-service-header-name}") String headerName) {
//        this.storage = storage;
//        this.headerName = headerName;
//    }

    @PostConstruct
    public void init() {
        System.out.println("Initializing bean !");
//        storage.createBook(Book.builder().id(1L).title("The Wonderful Book").author("The Guru").build());
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("Cleaning up bean !");
    }

    @Override
    public List<BookDTO> getBooks(String title) {
        List<Book> result = new ArrayList<>();
        if (title == null || title.isEmpty()) {
            result = storage.getAll();
        } else {
            result = storage.getAllContainingTitle(title);
        }
        return result.stream()
                    .map(item -> BookDTO.builder().author(item.getAuthor()).title(item.getTitle()).build())
                    .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ResponseEntity<BookDTO> getBook(Long id) {
        if (id < 1) {
            throw new IllegalArgumentException("BookId cannot be negative");
        }
        Optional<Book> result = storage.getBookById(id);

        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(BookDTO.builder().author(result.get().getAuthor()).title(result.get().getTitle()).build());
        }
    }

    @Override
    public ResponseEntity<BookDTO> createBook(BookDTO newBook) {
        try {
            Book b = storage.createBook(Book.builder().author(newBook.getAuthor()).title(newBook.getTitle()).build());
            return ResponseEntity.status(HttpStatus.CREATED)
                                .header(headerName, String.valueOf(b.getId()))
                                .body(BookDTO.builder().author(b.getAuthor()).title(b.getTitle()).build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<BookDTO> updateBook(Long id, BookDTO updatedBook) {
        Optional<Book> result = storage.updateBookById(id, Book.builder().author(updatedBook.getAuthor()).title(updatedBook.getTitle()).build());
        if (result.isEmpty()) {
            return createBook(updatedBook);
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(BookDTO.builder().author(result.get().getAuthor()).title(result.get().getTitle()).build());
        }
    }

    @Override
    public ResponseEntity<BookDTO> deleteBook(Long id) {
        Optional<Book> result = storage.deleteBookById(id);

        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok()
                                .header(headerName, String.valueOf(result.get().getId()))
                                .build();
        }
    }

}
