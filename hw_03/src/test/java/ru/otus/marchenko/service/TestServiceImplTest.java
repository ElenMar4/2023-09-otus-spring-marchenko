package ru.otus.marchenko.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.marchenko.dao.QuestionDao;
import ru.otus.marchenko.domain.Answer;
import ru.otus.marchenko.domain.Question;
import ru.otus.marchenko.domain.Student;
import ru.otus.marchenko.domain.TestResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Класс TestServiceImpl ")
class TestServiceImplTest {

    LocalizedIOService ioService;
    QuestionDao questionDao;
    TestService testService;
    TestResult testResult;
    List<Question> provideList = new ArrayList<>();
    Student student;

    @BeforeEach
    void setUp() {
        student = new Student("Ivan", "Ivanov");
        ioService = mock(LocalizedIOService.class);
        questionDao = mock(QuestionDao.class);
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
        testResult = new TestResult(student);
        when(questionDao.findAll()).thenReturn(provideList);
        when(ioService.readIntForRangeWithPromptLocalized(0,4,
                "TestService.enter.answer.student",
                "TestService.error.message")).thenReturn(1);
        testService = new TestServiceImpl(ioService, questionDao);
    }

    @Test
    @DisplayName("корректно определяет результат тестирования")
    public void determinesTestResult() {
        testResult = testService.executeTestFor(student);
        assertAll(
                () -> assertEquals(testResult.getStudent(), student),
                () -> assertEquals(testResult.getAnsweredQuestions(), provideList),
                () -> assertEquals(testResult.getRightAnswersCount(), 1)
        );
    }
}