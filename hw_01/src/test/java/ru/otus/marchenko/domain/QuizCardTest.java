package ru.otus.marchenko.domain;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс QuizCard ")
class QuizCardTest {

    private static QuizCard quizCard;

    @BeforeAll
    static void setUp() {
        quizCard = new QuizCard("1+1 = ",
                List.of(new Answer("1", false),
                        new Answer("2", true),
                        new Answer("3", false))
        );
    }

    @AfterAll
    static void tearDown() {
        quizCard = null;
    }

    @Test
    @DisplayName("корректно выдает правильный ответ")
    void getRightAnswer() {
        Answer expectedAnswer = new Answer("2", true);
        assertEquals(expectedAnswer, quizCard.getRightAnswer());
    }

    @Test
    @DisplayName("корректно выдает значение поля textQuestion")
    void getTextQuestion() {
        assertEquals("1+1 = ", quizCard.getTextQuestion());
    }

    @Test
    @DisplayName("корректно выдает значение поля answerChoices")
    void getAnswerChoices() {
        List<Answer> listExpectedAnswer = List.of(
                new Answer("1", false),
                new Answer("2", true),
                new Answer("3", false));
        assertEquals(listExpectedAnswer, quizCard.getAnswerChoices());
    }
}