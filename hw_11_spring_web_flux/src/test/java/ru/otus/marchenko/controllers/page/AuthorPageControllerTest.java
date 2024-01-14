package ru.otus.marchenko.controllers.page;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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