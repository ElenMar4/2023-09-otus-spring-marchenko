package ru.otus.marchenko.domain;

import java.util.List;

public class Quizlet {

    List<QuizCard> quizCardList;

    public void setQuizCardList(List<QuizCard> quizCardList) {
        this.quizCardList = quizCardList;
    }

    public List<QuizCard> getQuizCardList() {
        return quizCardList;
    }
}
