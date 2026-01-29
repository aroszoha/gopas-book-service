package cz.gopas.book.persistence;

import cz.gopas.book.bean.Book;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@ConditionalOnProperty(name = "custom.storage.type", havingValue = "memory")
public class InMemoryBookStorage implements BookStorage {

    private List<Book> storedBooks;

    public InMemoryBookStorage(List<Book> initialList) {
        this.storedBooks = initialList;
    }

    @Override
    public List<Book> getAll() {
        return new ArrayList<>(storedBooks);
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return storedBooks.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    @Override
    public Book createBook(Book b) {
        Long newId = 1L;
        if (!storedBooks.isEmpty()) {
            newId = storedBooks.getLast().getId() + 1L;
        }
        b.setId(newId);
        storedBooks.add(b);

        return b;
    }

    @Override
    public Optional<Book> updateBookById(Long id, Book b) {
        if (storedBooks.isEmpty()) {
            return Optional.empty();
        }

        Optional<Book> foundBook = storedBooks.stream().filter(item -> id == item.getId()).findFirst();
        if (foundBook.isEmpty()) {
            return Optional.empty();
        }

        if (b.getTitle() != null) {
            foundBook.get().setTitle(b.getTitle());
        }

        if (b.getAuthor() != null) {
            foundBook.get().setAuthor(b.getAuthor());
        }

        return foundBook;
    }

    @Override
    public Optional<Book> deleteBookById(Long id) {
        if (storedBooks.isEmpty()) {
            return Optional.empty();
        }

        Optional<Book> foundBook = storedBooks.stream().filter(item -> id == item.getId()).findFirst();
        if (foundBook.isPresent()) {
            storedBooks.remove(foundBook.get());
            return foundBook;
        } else {
            return Optional.empty();
        }
    }

}
