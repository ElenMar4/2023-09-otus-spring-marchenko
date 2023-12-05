package ru.otus.marchenko.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.marchenko.exceptions.EntityNotFoundException;
import ru.otus.marchenko.models.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JpaGenreRepository implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Genre> findAll() { return em.createQuery("select g from Genre g", Genre.class).getResultList();}

    @Override
    public Optional<Genre> findById(long id) { return Optional.ofNullable(em.find(Genre.class, id));}

    @Override
    public List<Genre> findAllByIds(Set<Long> ids) {
        List<Genre> genres = new ArrayList<>();
        ids = ids.stream().unordered().collect(Collectors.toSet());
        for (long id : ids) {
            genres.add(em.find(Genre.class, id));
        }
        return genres;
    }

    @Override
    public Optional<Genre> findByName(String genreName) {
        TypedQuery<Genre> query = em.createQuery(
                "select g from Genre g where g.name = :name"
                , Genre.class);
        query.setParameter("name", genreName);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Genre save(Genre genre) {
        if(genre.getId() == null) {
            em.persist(genre);
        } else em.merge(genre);
        return genre;
    }

    @Override
    public void deleteById(long id) {
        Genre genre = em.find(Genre.class, id);
        if(genre != null){
            em.remove(genre);
        }
    }
}
