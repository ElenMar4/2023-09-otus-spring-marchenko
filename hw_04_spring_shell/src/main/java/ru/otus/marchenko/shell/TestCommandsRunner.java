package ru.otus.marchenko.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import ru.otus.marchenko.domain.Student;
import ru.otus.marchenko.domain.TestResult;
import ru.otus.marchenko.service.ResultService;
import ru.otus.marchenko.service.StudentService;
import org.springframework.shell.Availability;
import ru.otus.marchenko.service.TestRunnerService;

@ShellComponent
@RequiredArgsConstructor
public class TestCommandsRunner {

    private final TestRunnerService runnerService;
    private final StudentService studentService;
    private final ResultService resultService;
    private TestResult result;
    private Student student;

    @ShellMethod(value = "Hello command", key = "hello")
    public String hello(){
        return "Здравствуйте! Это приложение для тестирования." +
                "\nДоступны следующие команды:" +
                "\nlogin - регистрация нового студента\n" +
                "start - начало тестирования\n" +
                "result - выводит результат тестирования\n" +
                "exit - выход.";
    }

    @ShellMethod(value = "Login command", key = "login")
    public void login() {
        student = studentService.determineCurrentStudent();
    }

    @ShellMethod(value = "Start test command", key = "start")
    @ShellMethodAvailability(value = "isStartTestCommandAvailable")
    public void startTest() {
        result = runnerService.run(student);
    }

    @ShellMethod(value = "Show result command", key = "result")
    @ShellMethodAvailability(value = "isShowResultTestCommandAvailable")
    public void showResultTest() {
        resultService.showResult(result);
    }

    private Availability isStartTestCommandAvailable() {
        return student == null ? Availability.unavailable("You must be logged in.") : Availability.available();
    }

    private Availability isShowResultTestCommandAvailable() {
        return result == null ? Availability.unavailable("You must be tested.") : Availability.available();
    }
}
