package ru.otus.marchenko.services;

import ru.otus.marchenko.dao.QuizletDao;

public class QuizletService {

    QuizletDao dao;

    public QuizletService(QuizletDao dao) {
        this.dao = dao;
    }

    public void startQuizlet() {
        PrintService printService = new PrintService(dao.getQuizlet());
        printService.printQuizlet();
    }
}
