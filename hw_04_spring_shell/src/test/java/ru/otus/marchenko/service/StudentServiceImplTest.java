package ru.otus.marchenko.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.marchenko.domain.Student;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Класс StudentServiceImplTest ")
@SpringBootTest(classes = StudentServiceImpl.class)
class StudentServiceImplTest {

    @MockBean
    LocalizedIOService ioService;
    @Autowired
    StudentService studentService;

    @BeforeEach
    void setUp() {
        when(ioService.readStringWithPromptLocalized("Please input your first name")).thenReturn("Ivan");
        when(ioService.readStringWithPromptLocalized("Please input your last name")).thenReturn("Ivanov");
        studentService = new StudentServiceImpl(ioService);
    }

    @Test
    @DisplayName("корректно создает нового студента по входящим данным")
    void correctCreateCurrentStudent() {
        Student student = studentService.determineCurrentStudent();
        assertThat(student.getFullName().equals("Ivan Ivanov"));
    }


}