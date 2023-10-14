package ru.otus.marchenko.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.marchenko.domain.Student;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Класс StudentServiceImplTest ")
class StudentServiceImplTest {

    IOService ioService;
    StudentService studentService;

    @BeforeEach
    void setUp() {
        ioService = mock(IOService.class);
        when(ioService.readStringWithPrompt("Please input your first name")).thenReturn("Ivan");
        when(ioService.readStringWithPrompt("Please input your last name")).thenReturn("Ivanov");
        studentService = new StudentServiceImpl(ioService);

    }

    @Test
    @DisplayName("корректно создает нового студента по входящим данным")
    void correctCreateCurrentStudent() {
        Student student = studentService.determineCurrentStudent();
        assertAll(
                () -> assertEquals("Ivan", student.firstName()),
                () -> assertEquals("Ivanov", student.lastName())
        );
    }


}