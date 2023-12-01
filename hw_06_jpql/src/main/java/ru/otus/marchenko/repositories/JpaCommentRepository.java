package ru.otus.marchenko.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.marchenko.exceptions.EntityNotFoundException;
import ru.otus.marchenko.models.Comment;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Comment> findById(long id) { return Optional.ofNullable(em.find(Comment.class, id));}

    @Override
    public List<Comment> findAllByBookId(long bookId) {
        TypedQuery<Comment> query = em.createQuery(
                "select c from Comment c where c.book.id = :id"
                , Comment.class);
        query.setParameter("id", bookId);
        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if(comment.getId() == null) {
            em.persist(comment);
        }else em.merge(comment);
        return comment;
    }

    @Override
    public void deleteById(long id) {
        Comment comment = Optional.ofNullable(em.find(Comment.class, id))
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        if(comment != null){
            em.remove(comment);
        }
    }
}
