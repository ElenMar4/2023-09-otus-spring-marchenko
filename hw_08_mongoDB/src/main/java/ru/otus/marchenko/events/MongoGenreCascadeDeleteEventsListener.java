package ru.otus.marchenko.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Genre;
import ru.otus.marchenko.repositories.BookRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MongoGenreCascadeDeleteEventsListener extends AbstractMongoEventListener<Genre> {

    private final BookRepository bookRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Genre> event) {
        super.onAfterDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        List<Book> books = bookRepository.findBooksByGenres(id);
        for (Book book : books) {
            if (book.getGenres().isEmpty()){
                book.setGenres(null);
            }
            bookRepository.save(book);
        }
    }
}

