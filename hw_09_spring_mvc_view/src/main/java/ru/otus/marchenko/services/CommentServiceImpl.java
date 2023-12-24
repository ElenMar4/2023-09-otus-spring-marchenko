package ru.otus.marchenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.marchenko.dto.comment.CommentCreateDto;
import ru.otus.marchenko.dto.comment.CommentDto;
import ru.otus.marchenko.exceptions.NotFoundException;
import ru.otus.marchenko.mappers.CommentMapper;
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
    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> findAllByBookId(Long bookId) {
        List<Comment> comments = commentRepository.findAllByBookId(bookId);
        return comments.stream().map(commentMapper::toDto).toList();
    }

    @Transactional
    @Override
    public void create(CommentCreateDto commentDto) {
        Book book = bookRepository.findById(commentDto.bookId())
                .orElseThrow(() -> new NotFoundException("Book not found"));
        Comment comment = commentMapper.toModel(commentDto);
        comment.setBook(book);
        commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void update(CommentDto commentDto) {
        Comment comment = commentMapper.toModel(commentDto);
        commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }
}
