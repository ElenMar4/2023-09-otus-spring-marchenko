package ru.otus.marchenko.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.marchenko.dao.QuizletDao;
import ru.otus.marchenko.domain.Answer;
import ru.otus.marchenko.domain.QuizCard;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@DisplayName("Класс QuizletService ")
class QuizletServiceTest {

    private static QuizCard quizCard;
    private QuizletService quizletService;

    @BeforeEach
    void setUp() {
        QuizletDao dao = mock(QuizletDao.class);
        IoService ioService = mock(IoService.class);
        quizletService = new QuizletService(dao, ioService);
        quizCard = new QuizCard("1+1 = ",
                List.of(new Answer("1", false),
                        new Answer("2", true),
                        new Answer("3", false))
        );
    }

    @AfterEach
    void tearDown() {
        quizCard = null;
    }

    @Test
    @DisplayName("корректно обрабатывает текст вопроса")
    void takeTextQuestion() {
        assertEquals("1+1 = ", quizletService.takeTextQuestionAsString(quizCard));
    }

    @Test
    @DisplayName("корректно обрабатывает варианты ответа")
    void takeAnswerChoices() {
        String str = "\n- 1\n- 2\n- 3";
        assertEquals(str, quizletService.takeAnswerChoicesAsString(quizCard));
    }

    @Test
    @DisplayName("коректно обрабатывает правильный ответ")
    void takeRightAnswer() {
        assertEquals("2", quizletService.takeRightAnswerAsString(quizCard));
    }
}