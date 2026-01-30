package cz.gopas.book;

import cz.gopas.book.bean.BookDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class BookServiceApplicationTests {

    @Autowired
    private RestTestClient testClient;

    @Test
    void contextLoads() {
    }

    @Test
    void testGetAllBooks() { // GET /v1/books/ => 200
        testClient.get().uri("/v1/books")
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    void testGetFirstBook() { // GET /v1/books/1 => 200 + Book JSON
        testClient.get().uri("/v1/books/1")
                .exchange()
                .expectBody(BookDTO.class)
                .consumeWith(resp -> "Java Definitive Guide2".equals(resp.getResponseBody().getTitle()));
    }

}
