package ru.otus.marchenko.dao;

import ru.otus.marchenko.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();
}
