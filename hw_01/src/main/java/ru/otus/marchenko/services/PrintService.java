package ru.otus.marchenko.services;

import ru.otus.marchenko.domain.QuizCard;
import ru.otus.marchenko.domain.Quizlet;

import java.util.List;

public class PrintService {

    private final Quizlet quizlet;

    public PrintService(Quizlet quizlet) {
        this.quizlet = quizlet;
    }

    public void printQuizlet() {
        for (QuizCard quizCard : quizlet.getQuizCardList()) {
            System.out.println("\n" + quizCard.getTextQuestion());
            List<String> choices = quizCard.getAnswerChoices();
            for (int i = 0; i < choices.size(); i++) {
                System.out.println((i + 1) + ") " + choices.get(i));
            }
            printRightAnswers(quizCard);
        }
    }

    public void printRightAnswers(QuizCard quizCard) {
        System.out.println("Right answer:  " + quizCard.getRightAnswer());
    }
}
