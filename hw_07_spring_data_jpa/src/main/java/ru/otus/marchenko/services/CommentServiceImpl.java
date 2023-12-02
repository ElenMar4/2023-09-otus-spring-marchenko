package ru.otus.marchenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.marchenko.exceptions.EntityNotFoundException;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Comment;
import ru.otus.marchenko.repositories.BookRepository;
import ru.otus.marchenko.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> findById(long id) { return commentRepository.findById(id);}

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAllByBookId(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));
        return commentRepository.findAll(Example.of(new Comment(null, null, book)));
    }

    @Transactional
    @Override
    public Comment insert(Long id, String message, Long bookId) {
        return save(null, message, bookId);
    }

    @Transactional
    @Override
    public Comment update(Long id, String message, Long bookId) {
        return save(id, message, null);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    private Comment save (Long id, String message, Long bookId){
        Comment comment;
        if (id == null){
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Book with id = %s not found", bookId)));
            comment = new Comment(null, message, book);
        } else {
            comment = commentRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
            comment.setMessage(message);
        }
        return commentRepository.save(comment);
    }
}
