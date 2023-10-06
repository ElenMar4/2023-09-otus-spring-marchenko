package ru.otus.marchenko.services;

import ru.otus.marchenko.dao.QuizletDao;
import ru.otus.marchenko.domain.Answer;
import ru.otus.marchenko.domain.QuizCard;

import java.util.List;

public class QuizletService {

    QuizletDao dao;
    IoService ioService;

    public QuizletService(QuizletDao dao, IoService ioService) {
        this.dao = dao;
        this.ioService = ioService;
    }

    public void startQuizlet() {
        List<QuizCard> quizCardList = dao.getAll();
        for (QuizCard card : quizCardList) {
            ioService.println(takeTextQuestionAsString(card));
            ioService.println(takeAnswerChoicesAsString(card));
            ioService.println("\n Right answer: ");
            ioService.println(takeRightAnswerAsString(card) + "\n");
        }
    }

    public String takeTextQuestionAsString(QuizCard quizCard) {
        return quizCard.getTextQuestion();
    }

    public String takeAnswerChoicesAsString(QuizCard quizCard) {
        StringBuilder result = new StringBuilder();
        List<String> listChoices = quizCard.getAnswerChoices().stream().map(Answer::getReply).toList();
        for (String answer : listChoices) {
            result.append("\n").append("- ").append(answer);
        }
        return result.toString();
    }

    public String takeRightAnswerAsString(QuizCard quizCard) {
        return quizCard.getRightAnswer().toString();
    }
}
