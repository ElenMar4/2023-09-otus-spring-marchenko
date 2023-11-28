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

    @Override
    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findAllByBookId(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            throw new EntityNotFoundException("Book not found");
        }
        return commentRepository.findAllByBookId(bookId);
    }

    @Transactional
    @Override
    public Comment insert(long bookId, String message) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Book with id = %s not found", bookId)));
        return commentRepository.save(new Comment(null, message, book));
    }

    @Transactional
    @Override
    public Comment deleteById(long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        commentRepository.deleteById(id);
        return comment.get();
    }
}
