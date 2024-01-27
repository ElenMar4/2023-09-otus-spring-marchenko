package ru.otus.marchenko.controllers.page;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
@ContextConfiguration(classes = {BookPageController.class})
class BookPageControllerTest {

    @Autowired
    private WebTestClient testClient;

    @Test
    @DisplayName("Should return model books and list view")
    public void shouldReturnBooksAndListView() throws Exception {
        testClient.get().uri("/book")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .toString().contains("book/list");
    }

    @Test
    @DisplayName("Should return add view")
    public void shouldReturnAddView() throws Exception {
        testClient.get().uri("/book/add")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .toString().contains("book/add");
    }

    @Test
    @DisplayName("Should return model book and view book/edit")
    public void shouldReturnBookAndEditView() throws Exception {
        testClient.get().uri("/book/edit/{id}", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .toString().contains("book/edit");
    }

    @Test
    @DisplayName("Should return model book and view book/details")
    public void shouldReturnBookAndDetailsView() throws Exception {
        testClient.get().uri("/book/details/{id}", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .toString().contains("book/details");
    }
}