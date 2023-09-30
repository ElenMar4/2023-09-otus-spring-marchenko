package ru.otus.marchenko.dao.parser;

import ru.otus.marchenko.dao.reader.ResourceReading;
import ru.otus.marchenko.domain.QuizCard;

import java.util.List;

public interface Parser {
    void setResourceReading(ResourceReading resourceReading);

    List<QuizCard> parse();
}
