package ru.otus.marchenko.service;

import ru.otus.marchenko.domain.Student;
import ru.otus.marchenko.domain.TestResult;

public interface TestRunnerService {
    TestResult run(Student student);
}
