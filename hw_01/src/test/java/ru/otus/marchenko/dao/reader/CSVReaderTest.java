package ru.otus.marchenko.dao.reader;

import org.junit.jupiter.api.*;

@DisplayName("Класс CSVReader ")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CSVReaderTest {

    @Test
    @DisplayName("выбрасывает исключение, если файл не найден")
    void testExpectedExceptionReadTextResourceForLine() {
        CSVReader csvReader = new CSVReader();
        csvReader.setFileName("notFoundFile.csv");
        Assertions.assertThrows(IllegalArgumentException.class, csvReader::readTextResourceForLine);
    }
}