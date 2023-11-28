package ru.otus.marchenko.repositories;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.marchenko.exceptions.EntityNotFoundException;
import ru.otus.marchenko.models.Book;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genres-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint(FETCH.getKey(), entityGraph);
        return query.getResultList();
    }

    @Override
    public Optional<Book> findById(long id) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genres-entity-graph");
        Map<String, Object> properties = new HashMap<>();
        properties.put(FETCH.getKey(), entityGraph);
        return Optional.ofNullable(em.find(Book.class, id, properties));
    }

    @Override
    public Optional<Book> findCopyByTitleAndAuthor(String title, Long authorId) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genres-entity-graph");
        TypedQuery<Book> query = em.createQuery(
                "select b from Book b where b.title = :title and b.author.id = :authorId"
                , Book.class);
        query.setParameter("title", title);
        query.setParameter("authorId", authorId);
        query.setHint(FETCH.getKey(), entityGraph);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
        } else {
            em.merge(book);
        }
        return book;
    }

    @Override
    public Book deleteById(long id) {
        Book book = findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
        em.remove(book);
        return book;
    }
}
