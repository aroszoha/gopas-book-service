package cz.gopas.book.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * Class used for REST communication.
 * Not all fields are exposed from persisted entity.
 */
public class BookDTO {

    private String title;
    private String author;

}
