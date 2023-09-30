package ru.otus.marchenko.dao;

import ru.otus.marchenko.dao.parser.Parser;
import ru.otus.marchenko.domain.Quizlet;

public class QuizletDaoImpl implements QuizletDao {
    Parser parser;

    public QuizletDaoImpl(Parser parser) {
        this.parser = parser;
    }

    @Override
    public Quizlet getQuizlet() {
        Quizlet quizlet = new Quizlet();
        quizlet.setQuizCardList(parser.parse());
        return quizlet;
    }
}
