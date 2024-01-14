package ru.otus.marchenko.controllers.page;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentPageControllerTest {

    @Autowired
    private WebTestClient testClient;

    @Test
    @DisplayName("Should return correct all comments by book")
    void shouldReturnCorrectAllCommentByBookIdInPath() throws Exception {
        testClient.get().uri("/comment/{id}", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .toString().contains("comment/list");
    }

    @Test
    @DisplayName("Should return view comment/add")
    public void shouldReturnAddView() throws Exception {
        testClient.get().uri("/comment/add/{id}", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody().returnResult()
                .toString().contains("comment/add");
    }
}