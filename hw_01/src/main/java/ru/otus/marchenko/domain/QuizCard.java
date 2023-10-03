package ru.otus.marchenko.domain;

import java.util.List;
import java.util.Objects;

public class QuizCard {
    String textQuestion;
    List<Answer> answerChoices;

    public QuizCard(String textQuestion, List<Answer> choices) {
        this.textQuestion = textQuestion;
        this.answerChoices = choices;
    }

    public Answer getRightAnswer() {
        Answer rightAnswer = answerChoices.stream().filter(Answer::isRight).findFirst().orElse(null);
        if (rightAnswer == null) {
            throw new NullPointerException("Empty value in rightAnswer");
        }
        return rightAnswer;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public List<Answer> getAnswerChoices() {
        return answerChoices;
    }

    @Override
    public String toString() {
        return textQuestion + "\n" + getAnswerChoices().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuizCard quizCard)) return false;
        return Objects.equals(textQuestion, quizCard.textQuestion) && Objects.equals(answerChoices, quizCard.answerChoices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textQuestion, answerChoices);
    }
}
