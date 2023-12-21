package ru.otus.marchenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.marchenko.dto.comment.CommentCreateDto;
import ru.otus.marchenko.dto.comment.CommentDto;
import ru.otus.marchenko.exceptions.EntityNotFoundException;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Comment;
import ru.otus.marchenko.repositories.BookRepository;
import ru.otus.marchenko.repositories.CommentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> findAllByBookId(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));
        return commentRepository.findAll(Example.of(new Comment(null, null, book))).stream().map(CommentDto::new).toList();
    }

    @Transactional
    @Override
    public void insert(CommentCreateDto commentDto) {
        Book book = bookRepository.findById(commentDto.bookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        commentRepository.save(new Comment(null, commentDto.message(), book));
    }

    @Transactional
    @Override
    public void update(Comment commentChange) {
        commentRepository.save(commentChange);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }
}
