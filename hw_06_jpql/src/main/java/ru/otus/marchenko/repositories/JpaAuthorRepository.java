package ru.otus.marchenko.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.marchenko.exceptions.EntityNotFoundException;
import ru.otus.marchenko.models.Author;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaAuthorRepository implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Author> findAll() { return em.createQuery("select a from Author a", Author.class).getResultList();}

    @Override
    public Optional<Author> findById(long id) { return Optional.ofNullable(em.find(Author.class, id));}

    @Override
    public Optional<Author> findByName(String name) {
        TypedQuery<Author> query = em.createQuery(
                "select a from Author a where a.fullName = :name"
                , Author.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Author save(Author author) {
        if(author.getId() == null) {
            em.persist(author);
        }else em.merge(author);
        return author;
    }

    @Override
    public Author deleteById(long id) {
        Author author = Optional.ofNullable(em.find(Author.class, id))
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
        if(author !=null) {
            em.remove(author);
        }
        return author;
    }
}
