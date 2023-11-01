package ru.otus.marchenko.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.marchenko.domain.Student;
import ru.otus.marchenko.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    @Override
    public TestResult run(Student student) {
        return testService.executeTestFor(student);
    }
}
