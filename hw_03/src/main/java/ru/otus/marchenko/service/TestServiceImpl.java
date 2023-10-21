package ru.otus.marchenko.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.marchenko.dao.QuestionDao;
import ru.otus.marchenko.domain.Student;
import ru.otus.marchenko.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            ioService.printFormattedLineLocalized("MessagesService.out.question", question.text());
            ioService.printFormattedLineLocalized("MessagesService.out.choices.answers",
                    question.answers().get(0).text(),
                    question.answers().get(1).text(),
                    question.answers().get(2).text());
            int countAnswers = question.answers().size();
            int studentAnswer = ioService.readIntForRangeWithPromptLocalized(
                    0,
                    countAnswers+1,
                    "TestService.enter.answer.student",
                    "TestService.error.message");
            boolean isAnswerValid = question.answers().get(studentAnswer-1).isCorrect();
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }
}
