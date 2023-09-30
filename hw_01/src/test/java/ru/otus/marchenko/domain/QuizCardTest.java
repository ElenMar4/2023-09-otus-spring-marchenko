package ru.otus.marchenko.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс QuizCard ")
class QuizCardTest {

    @Test
    @DisplayName("корректно создается конструктором")
    void shouldHaveCorrectConstructor(){
        QuizCard quizCard = new QuizCard("1+1 = ",
                                        List.of("1", "2", "3"),
                                        "2");
        assertAll("quizCard",
                ()->assertEquals("1+1 = ", quizCard.getTextQuestion()),
                ()->assertEquals(List.of("1", "2", "3"), quizCard.getAnswerChoices()),
                ()->assertEquals("2", quizCard.getRightAnswer())
        );
    }

    @Test
    @DisplayName("корректно выдает значение поля rightAnswer")
    void getRightAnswer() {
        QuizCard quizCard = new QuizCard(null, null, "answer");
        assertEquals("answer", quizCard.getRightAnswer());
    }

    @Test
    @DisplayName("корректно выдает значение поля textQuestion")
    void getTextQuestion() {
        QuizCard quizCard = new QuizCard("question", null, null);
        assertEquals("question", quizCard.getTextQuestion());
    }

    @Test
    @DisplayName("корректно выдает значение поля answerChoices")
    void getAnswerChoices() {
        QuizCard quizCard = new QuizCard(null, List.of("a", "b"), null);
        assertEquals(List.of("a", "b"), quizCard.getAnswerChoices());
    }
}