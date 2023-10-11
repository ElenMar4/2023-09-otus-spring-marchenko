package ru.otus.marchenko.service;

import lombok.RequiredArgsConstructor;
import ru.otus.marchenko.dao.QuestionDao;
import ru.otus.marchenko.domain.Question;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        for (Question question : questionDao.findAll()) {
            ioService.printFormattedLine("%s\n1. %s\n2. %s\n3. %s\n",
                    question.text(),
                    question.answers().get(0).text(),
                    question.answers().get(1).text(),
                    question.answers().get(2).text());
        }
    }
}
