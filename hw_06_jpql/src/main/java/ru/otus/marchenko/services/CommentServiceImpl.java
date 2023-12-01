package ru.otus.marchenko.services;

import lombok.RequiredArgsConstructor;
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
        return commentRepository.findAllByBookId(bookId);
    }

    @Transactional
    @Override
    public Comment insert(long bookId, String message) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Book with id = %s not found", bookId)));
        if(book !=null){
            return commentRepository.save(new Comment(null, message, book));
        }
        return null;
    }

    @Transactional
    @Override
    public Comment update(long commentId, String message) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        if (comment != null){
            comment.setMessage(message);
            return commentRepository.save(comment);
        }
        return null;
    }

    @Transactional
    @Override
    public Comment deleteById(long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        commentRepository.deleteById(id);
        return comment.get();
    }
}
