package ru.otus.marchenko.dao.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.marchenko.dao.reader.ResourceReading;
import ru.otus.marchenko.domain.QuizCard;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс ParserImpl ")
class ParserImplTest {


    private ResourceReading resourceReading;
    Parser parser = new ParserImpl();

    @BeforeEach
    void setUp() {
        resourceReading = Mockito.mock(ResourceReading.class);
        this.parser.setResourceReading(resourceReading);
    }

    @Test
    @DisplayName("преобразует строку в объект QuizCard")
    void parse() {
        given(resourceReading.readTextResourceForLine())
                .willReturn(List.of(
                        "1+1=,0,1,2,2"
                ));
        List<QuizCard> list = parser.parse();
        for (QuizCard quizCard : list) {
            assertAll("quizCard",
                    () -> assertEquals("1+1=", quizCard.getTextQuestion()),
                    () -> assertEquals(List.of("0", "1", "2"), quizCard.getAnswerChoices()),
                    () -> assertEquals("2", quizCard.getRightAnswer()));
        }
    }
}