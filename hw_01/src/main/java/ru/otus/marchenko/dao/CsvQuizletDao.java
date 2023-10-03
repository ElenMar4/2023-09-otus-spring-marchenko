package ru.otus.marchenko.dao;

import ru.otus.marchenko.domain.Answer;
import ru.otus.marchenko.domain.QuizCard;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvQuizletDao implements QuizletDao {

    private final List<QuizCard> quizCardList;
    private static String fileName;

    public CsvQuizletDao(String fileName) {
        CsvQuizletDao.fileName = fileName;
        this.quizCardList = parse();
    }

    @Override
    public List<QuizCard> getAll() {
        return quizCardList;
    }

    public static List<QuizCard> parse() {

        List<QuizCard> quiz = new ArrayList<>();
        List<String> textResource = readTextResourceForLine();
        for (String line : textResource) {
            quiz.add(convertStringToQuizCard(line));
        }
        return quiz;
    }

    public static List<String> readTextResourceForLine() {
        List<String> listLine = new ArrayList<>();
        ClassLoader classLoader = CsvQuizletDao.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                while (reader.ready()) {
                    listLine.add(reader.readLine());
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return listLine;
    }

    public static QuizCard convertStringToQuizCard(String line) {
        List<Answer> choicesAnswer = new ArrayList<>();

        String[] text = line.split(",");
        String textQuestion = text[0];
        String flag = "*";
        for (int i = 1; i < text.length; i++) {
            if (text[i].startsWith(flag)) {
                choicesAnswer.add(new Answer(text[i].substring(1), true));
            } else {
                choicesAnswer.add(new Answer(text[i], false));
            }
        }
        return new QuizCard(textQuestion, choicesAnswer);
    }
}
