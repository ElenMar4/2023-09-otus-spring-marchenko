package ru.otus.marchenko.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.marchenko.config.TestFileNameProvider;
import ru.otus.marchenko.dao.dto.QuestionDto;
import ru.otus.marchenko.domain.Question;
import ru.otus.marchenko.exceptions.QuestionReadException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        List<Question> questionList;
        ClassLoader classLoader = getClass().getClassLoader();
        try (var inputStream = classLoader.getResourceAsStream(fileNameProvider.getTestFileName());
             var inputStreamReader = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
             var reader = new BufferedReader(inputStreamReader)
        ) {
            List<QuestionDto> questionDtoList ;
            var toBean = new CsvToBeanBuilder<QuestionDto>(reader)
                    .withType(QuestionDto.class)
                    .withSeparator(';')
                    .build();
            questionDtoList = toBean.parse();
            questionList = questionDtoList.stream().map(QuestionDto::toDomainObject).toList();
        } catch (Exception ex) {
            throw new QuestionReadException("File read error", ex);
        }
        return questionList;
    }
}
