package cz.gopas.book.persistence;

import cz.gopas.book.bean.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Primary
@Service
@ConditionalOnProperty(name = "custom.storage.type", havingValue = "db")
public class DatabaseBookStorage implements BookStorage {

    private final BookRepository bookRepo;

    @Override
    public List<Book> getAll() {
//        return bookRepo.findAll();
        return bookRepo.getAllBooks();
    }

    @Override
    public List<Book> getAllContainingTitle(String title) {
        return bookRepo.findBooksByTitleContains(title);
    }

    @Override
    public Optional<Book> getBookById(Long id) {
//        return bookRepo.findById(id);
        return bookRepo.getRecordById(id);
    }

    @Override
    public Book createBook(Book b) {
        return bookRepo.save(b);
    }

    @Override
    public Optional<Book> updateBookById(Long id, Book b) {
        Optional<Book> foundBook = getBookById(id);
        if (foundBook.isEmpty()) {
            return Optional.empty();
        }

        if (b.getTitle() != null) {
            foundBook.get().setTitle(b.getTitle());
        }

        if (b.getAuthor() != null) {
            foundBook.get().setAuthor(b.getAuthor());
        }

        bookRepo.save(foundBook.get());

        return foundBook;
    }

    @Override
    public Optional<Book> deleteBookById(Long id) {
        Optional<Book> foundBook = getBookById(id);
        if (foundBook.isEmpty()) {
            return Optional.empty();
        } else {
            bookRepo.delete(foundBook.get());
            return foundBook;
        }
    }
}
