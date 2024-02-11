package ru.otus.marchenko.config.steps;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.marchenko.models.noSqlModels.AuthorDocument;
import ru.otus.marchenko.models.sqlModels.AuthorTable;
import ru.otus.marchenko.services.ConversionService;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@RequiredArgsConstructor
public class AuthorStepConfig {

    @Autowired
    private final JobRepository jobRepository;

    @Autowired
    private final PlatformTransactionManager platformTransactionManager;

    private static final int CHUNK_SIZE = 5;
    private final Logger logger = LoggerFactory.getLogger("Batch");

    public static final String JPA_AUTHOR_ITEM_READER_NAME = "jpaAuthorItemReader";
    public static final String CONVERSION_AUTHOR_STEP_NAME = "conversionAuthorStep";

    @Bean
    public ItemReader<AuthorTable> readerAuthor(EntityManagerFactory factory) {
        return new JpaCursorItemReaderBuilder<AuthorTable>()
                .name(JPA_AUTHOR_ITEM_READER_NAME)
                .entityManagerFactory(factory)
                .saveState(true)
                .queryString("select a from AuthorTable a")
                .build();
    }

    @Bean
    public ItemProcessor<AuthorTable, AuthorDocument> processorAuthor(ConversionService conversionService) {
        return conversionService::convertAuthor;
    }

    @Bean
    public ItemWriter<AuthorDocument> writerAuthor(MongoTemplate template) {
        return new MongoItemWriterBuilder<AuthorDocument>()
                .template(template)
                .collection("authors")
                .build();
    }

    @Bean
    public Step conversionAuthorStep(ItemReader<AuthorTable> readerAuthor,
                                ItemWriter<AuthorDocument> writerAuthor,
                                ItemProcessor<AuthorTable, AuthorDocument> processorAuthor) {
        return new StepBuilder(CONVERSION_AUTHOR_STEP_NAME, jobRepository)
                .<AuthorTable, AuthorDocument>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(readerAuthor)
                .processor(processorAuthor)
                .writer(writerAuthor)
                .listener(new ItemReadListener<AuthorTable>() {
                    @Override
                    public void beforeRead() {
                        logger.info("Start read Author from sqlDB");
                    }

                    @Override
                    public void afterRead(AuthorTable authorTable) {
                        logger.info("End read. Author is {}", authorTable.getFullName());
                    }

                    @Override
                    public void onReadError(Exception ex) {
                        logger.info("Error when we try to read authors from sqlDB");
                    }
                }
                ).build();
    }
}
