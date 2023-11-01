package ru.otus.marchenko.service;

import ru.otus.marchenko.domain.Student;
import ru.otus.marchenko.domain.TestResult;

public interface TestService {
    TestResult executeTestFor(Student student);
}
