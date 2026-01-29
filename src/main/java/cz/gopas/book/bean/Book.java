package cz.gopas.book.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@NamedQueries({
        @NamedQuery(name = "Book.getById", query = "SELECT book FROM Book book WHERE book.id = :id"),
        @NamedQuery(name = "Book.updateTitleById", query = "UPDATE Book book SET book.title = :title WHERE book.id = :id")
})
//@SecondaryTable(name="book_detail")
/**
 * Class used for entity persistence.
 */
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
//    @Column(table="book_detail")
//    @Basic(fetch = FetchType.LAZY) // N + 1
//    private byte[] cover;

}
