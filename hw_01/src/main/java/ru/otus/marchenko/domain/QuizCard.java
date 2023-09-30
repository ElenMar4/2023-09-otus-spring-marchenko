package ru.otus.marchenko.domain;

import java.util.List;

public class QuizCard {
    String textQuestion;
    List<String> answerChoices;
    String rightAnswer;

    public QuizCard(String textQuestion, List<String> choices, String rightAnswer) {
        this.textQuestion = textQuestion;
        this.answerChoices = choices;
        this.rightAnswer = rightAnswer;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public List<String> getAnswerChoices() {
        return answerChoices;
    }
}
