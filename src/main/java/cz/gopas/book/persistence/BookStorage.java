package cz.gopas.book.persistence;

import cz.gopas.book.bean.Book;

import java.util.List;
import java.util.Optional;

public interface BookStorage {

    List<Book> getAll();
    default List<Book> getAllContainingTitle(String title) {
        return getAll();
    }
    Optional<Book> getBookById(Long id);
    Book createBook(Book b);
    Optional<Book> updateBookById(Long id, Book b);
    Optional<Book> deleteBookById(Long id);


}
