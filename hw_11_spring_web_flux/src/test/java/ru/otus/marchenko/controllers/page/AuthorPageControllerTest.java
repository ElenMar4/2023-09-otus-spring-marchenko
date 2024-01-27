package ru.otus.marchenko.controllers.page;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
@ContextConfiguration(classes = {AuthorPageController.class})
class AuthorPageControllerTest {

    @Autowired
    private WebTestClient testClient;

    @Test
    @DisplayName("Should return view author/add")
    public void shouldReturnAddView() throws Exception {
        testClient.get().uri("/author/add")
                .exchange()
                .expectStatus().isOk()
                .expectBody().returnResult()
                .toString().contains("author/add");
    }
}