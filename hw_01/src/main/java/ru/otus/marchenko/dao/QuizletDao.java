package ru.otus.marchenko.dao;

import ru.otus.marchenko.domain.QuizCard;

import java.util.List;

public interface QuizletDao {
    List<QuizCard> getAll();
}
