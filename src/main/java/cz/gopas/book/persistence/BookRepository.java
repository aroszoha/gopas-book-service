package cz.gopas.book.persistence;

import cz.gopas.book.bean.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBooksByTitleContains(String title); // SELECT * FROM book WHERE title LIKE ''

    @Query("SELECT book FROM Book book")
    List<Book> getAllBooks(); // SELECT * FROM book

//    @Query("SELECT book FROM Book book WHERE book.id = ?1")
//    @Query("SELECT book FROM Book book WHERE book.id = :id")
//    @Query(value="SELECT * FROM book WHERE id = :id", nativeQuery = true)
    @Query(name = "Book.getById")
    Optional<Book> getRecordById(@Param("id") Long id);

    @Modifying
//    @Query("UPDATE Book book SET book.title = :title WHERE book.id = :id")
    @Query(name = "Book.updateTitleById")
    void updateBookTitle(@Param("id") Long id, @Param("title") String title);

}
