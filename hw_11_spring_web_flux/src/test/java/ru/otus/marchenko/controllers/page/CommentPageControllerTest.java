package ru.otus.marchenko.controllers.page;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
@ContextConfiguration(classes = {CommentPageController.class})
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