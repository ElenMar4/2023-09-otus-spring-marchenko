package ru.otus.marchenko;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.marchenko.services.QuizletService;

public class SpringApp {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuizletService quizletService = context.getBean(QuizletService.class);
        quizletService.startQuizlet();
    }
}