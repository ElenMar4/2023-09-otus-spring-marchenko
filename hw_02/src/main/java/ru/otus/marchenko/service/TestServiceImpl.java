package ru.otus.marchenko.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.marchenko.dao.QuestionDao;
import ru.otus.marchenko.domain.Student;
import ru.otus.marchenko.domain.TestResult;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);
        for (var question : questions) {
            ioService.printFormattedLine("%s\n1. %s\n2. %s\n3. %s\n",
                    question.text(),
                    question.answers().get(0).text(),
                    question.answers().get(1).text(),
                    question.answers().get(2).text());
            String studentAnswer;
            boolean isValidEnter;
            do {
                ioService.printLine("Enter number your answer:");
                studentAnswer = ioService.readString();
                isValidEnter = isValidEnter(studentAnswer, question.answers().size());
            } while (!isValidEnter);
            int numberAnswer = Integer.parseInt(studentAnswer) - 1;
            var isAnswerValid = question.answers().get(numberAnswer).isCorrect();
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    public boolean isValidEnter(String studentAnswer, int countRangeAnswers) {
        if (!(studentAnswer.isEmpty()) && (studentAnswer.matches("[1-3]"))) {
            return true;
        }
        ioService.printLine("Your enter is not valid. Please enter a number from a range: [1 - " + countRangeAnswers + "]");
        return false;
    }
}
