package ru.otus.marchenko.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.marchenko.config.TestFileNameProvider;
import ru.otus.marchenko.domain.Answer;
import ru.otus.marchenko.domain.Question;
import ru.otus.marchenko.exceptions.QuestionReadException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CsvQuestionDaoTest {
    private TestFileNameProvider fileNameProvider;
    private QuestionDao questionDao;
    List<Question> provideList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        fileNameProvider = mock(TestFileNameProvider.class);
        questionDao = new CsvQuestionDao(fileNameProvider);
        provideList.add(new Question("1= ?", List.of(
                new Answer("1", true),
                new Answer("2", false),
                new Answer("3", false)))
        );
        provideList.add(new Question("2= ?", List.of(
                new Answer("1", false),
                new Answer("2", true),
                new Answer("3", false)))
        );
        provideList.add(new Question("3= ?", List.of(
                new Answer("1", false),
                new Answer("2", false),
                new Answer("3", true)))
        );
    }

    @Test
    @DisplayName("корректно разбирает данные из CSV-файла и преобразует в список объектов Questuion")
    public void parseCSVFileAndCorrectGetListQuestions() {
        when(fileNameProvider.getTestFileName()).thenReturn("testQuestions.csv");
        List<Question> parsList = questionDao.findAll();
        assertThat(provideList.equals(parsList)).isTrue();
    }

    @Test
    @DisplayName("выбрасывает исключение типа QuestionReadException, когда файл для чтения не найден")
    public void methodFindAllThrowsExceptionWhenFileNotFound() {
        when(fileNameProvider.getTestFileName()).thenReturn("notFindFile.csv");
        assertThatThrownBy(() -> questionDao.findAll())
                .isInstanceOf(QuestionReadException.class)
                .hasMessageContaining("File read error");
    }
}
