package ru.otus.marchenko.config.steps;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.marchenko.models.noSqlModels.BookDocument;
import ru.otus.marchenko.models.sqlModels.BookTable;
import ru.otus.marchenko.services.ConversionService;

@Configuration
@RequiredArgsConstructor
public class BookStepConfig {

    @Autowired
    private final JobRepository jobRepository;

    @Autowired
    private final PlatformTransactionManager platformTransactionManager;

    private static final int CHUNK_SIZE = 5;
    private final Logger logger = LoggerFactory.getLogger("Batch");

    public static final String JPA_BOOK_ITEM_READER_NAME = "jpaBookItemReader";
    public static final String CONVERSION_BOOK_STEP_NAME = "conversionBookStep";

    @Bean
    public ItemReader<BookTable> readerBook(EntityManagerFactory factory) {
        return new JpaCursorItemReaderBuilder<BookTable>()
                .name(JPA_BOOK_ITEM_READER_NAME)
                .entityManagerFactory(factory)
                .saveState(true)
                .queryString("select b from BookTable b")
                .build();
    }

    @Bean
    public ItemProcessor<BookTable, BookDocument> processorBook(ConversionService conversionService) {
        return conversionService::convertBook;
    }

    @Bean
    public ItemWriter<BookDocument> writerBook(MongoTemplate template) {
        return new MongoItemWriterBuilder<BookDocument>()
                .template(template)
                .collection("books")
                .build();
    }

    @Bean
    public Step conversionBookStep(ItemReader<BookTable> readerBook,
                                    ItemWriter<BookDocument> writerBook,
                                    ItemProcessor<BookTable, BookDocument> processorBook) {
        return new StepBuilder(CONVERSION_BOOK_STEP_NAME, jobRepository)
                .<BookTable, BookDocument>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(readerBook)
                .processor(processorBook)
                .writer(writerBook)
                .listener(new ItemReadListener<BookTable>() {
                    @Override
                    public void beforeRead() {
                        logger.info("Start read books from sqlDB");
                    }

                    @Override
                    public void afterRead(BookTable bookTable) {
                        logger.info("End read. Book is {}", bookTable.getTitle());
                    }

                    @Override
                    public void onReadError(Exception ex) {
                        logger.info("Error when we try to read books from sqlDB");
                    }
                }).build();
    }
}
