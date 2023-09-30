package ru.otus.marchenko.dao.parser;

import ru.otus.marchenko.dao.reader.ResourceReading;
import ru.otus.marchenko.domain.QuizCard;

import java.util.ArrayList;
import java.util.List;

public class ParserImpl implements Parser {
    ResourceReading resourceReading;

    public void setResourceReading(ResourceReading resourceReading) {
        this.resourceReading = resourceReading;
    }

    public List<QuizCard> parse() {
        List<QuizCard> questions = new ArrayList<>();
        List<String> textResource = resourceReading.readTextResourceForLine();
        for (String line : textResource) {
            String[] text = line.split(",");
            String textQuestion = text[0];
            List<String> choices = new ArrayList<>();
            for (int i = 1; i < text.length - 1; i++) {
                choices.add(text[i]);
            }
            String rightAnswer = text[text.length - 1];
            questions.add(new QuizCard(textQuestion, choices, rightAnswer));
        }
        return questions;
    }
}
