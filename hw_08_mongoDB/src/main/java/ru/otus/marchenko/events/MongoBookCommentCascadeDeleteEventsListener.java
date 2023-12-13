package ru.otus.marchenko.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Comment;
import ru.otus.marchenko.repositories.CommentRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MongoBookCommentCascadeDeleteEventsListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        List<Comment> comments = commentRepository.findAllByBook(id);
        for (Comment comment : comments) {
            //если комментарий удаляется вместе с книгой
            commentRepository.deleteById(comment.getId());

            //если комментарий остается, но удаляется ссылка на книгу
//            comment.setBook(null);
//            commentRepository.save(comment);
        }
    }
}
