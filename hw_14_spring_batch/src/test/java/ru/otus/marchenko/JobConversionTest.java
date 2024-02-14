package ru.otus.marchenko;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.marchenko.config.JobConfig;
import ru.otus.marchenko.config.steps.AuthorStepConfig;
import ru.otus.marchenko.config.steps.BookStepConfig;
import ru.otus.marchenko.config.steps.CommentsStepConfig;
import ru.otus.marchenko.config.steps.GenreStepConfig;
import ru.otus.marchenko.models.noSqlModels.AuthorDocument;
import ru.otus.marchenko.models.noSqlModels.BookDocument;
import ru.otus.marchenko.models.noSqlModels.CommentDocument;
import ru.otus.marchenko.models.noSqlModels.GenreDocument;
import ru.otus.marchenko.services.ConversionService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.otus.marchenko.config.JobConfig.IMPORT_JOB_NAME;

@SpringBootTest
@SpringBatchTest
@Import({JobConfig.class, ConversionService.class, AuthorStepConfig.class, GenreStepConfig.class,
        BookStepConfig.class, CommentsStepConfig.class})
public class JobConversionTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void clearMetaData() {
        mongoTemplate.dropCollection(AuthorDocument.class);
        mongoTemplate.dropCollection(GenreDocument.class);
        mongoTemplate.dropCollection(BookDocument.class);
        mongoTemplate.dropCollection(CommentDocument.class);
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    @DisplayName("Should correct load job")
    void testLoadJob() throws Exception {
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(IMPORT_JOB_NAME);
    }

    @Test
    @DisplayName("Should run job with status - COMPLETED and correct create documents to MongoDB")
    void testRunJob() throws Exception {
        assertThat(mongoTemplate.findAll(BookDocument.class).size()).isEqualTo(0);
        assertThat(mongoTemplate.findAll(AuthorDocument.class).size()).isEqualTo(0);
        assertThat(mongoTemplate.findAll(GenreDocument.class).size()).isEqualTo(0);
        assertThat(mongoTemplate.findAll(CommentDocument.class).size()).isEqualTo(0);

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(new JobParametersBuilder()
                .addLocalDateTime("time", LocalDateTime.now())
                .toJobParameters());
        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        List<AuthorDocument> authorDocuments = mongoTemplate.findAll(AuthorDocument.class);
        List<GenreDocument> genreDocuments = mongoTemplate.findAll(GenreDocument.class);
        List<BookDocument> bookDocuments = mongoTemplate.findAll(BookDocument.class);
        List<CommentDocument> commentDocuments = mongoTemplate.findAll(CommentDocument.class);

        assertThat(authorDocuments.size()).isEqualTo(1);
        assertThat(genreDocuments.size()).isEqualTo(1);
        assertThat(bookDocuments.size()).isEqualTo(1);
        assertThat(commentDocuments.size()).isEqualTo(1);

        assertThat(bookDocuments.get(0).getAuthor().getId()).isEqualTo(authorDocuments.get(0).getId());
        assertThat(bookDocuments.get(0).getGenre().getId()).isEqualTo(genreDocuments.get(0).getId());
        assertThat(commentDocuments.get(0).getBook().getId()).isEqualTo(bookDocuments.get(0).getId());
    }
}
