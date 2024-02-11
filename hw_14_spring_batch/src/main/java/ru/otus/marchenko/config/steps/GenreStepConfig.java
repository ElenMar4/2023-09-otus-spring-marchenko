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
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.marchenko.models.noSqlModels.GenreDocument;
import ru.otus.marchenko.models.sqlModels.GenreTable;
import ru.otus.marchenko.services.ConversionService;

@Configuration
@RequiredArgsConstructor
public class GenreStepConfig {
    @Autowired
    private final JobRepository jobRepository;

    @Autowired
    private final PlatformTransactionManager platformTransactionManager;

    private static final int CHUNK_SIZE = 5;
    private final Logger logger = LoggerFactory.getLogger("Batch");

    public static final String JPA_GENRE_ITEM_READER_NAME = "jpaGenreItemReader";
    public static final String CONVERSION_GENRE_STEP_NAME = "conversionGenreStep";

    @Bean
    public JpaCursorItemReader<GenreTable> readerGenre(EntityManagerFactory factory) {
        return new JpaCursorItemReaderBuilder<GenreTable>()
                .name(JPA_GENRE_ITEM_READER_NAME)
                .entityManagerFactory(factory)
                .saveState(true)
                .queryString("select g from GenreTable g")
                .build();
    }

    @Bean
    public ItemProcessor<GenreTable, GenreDocument> processorGenre(ConversionService conversionService) {
        return conversionService::convertGenre;
    }

    @Bean
    public MongoItemWriter<GenreDocument> writerGenre(MongoTemplate template) {
        return new MongoItemWriterBuilder<GenreDocument>()
                .template(template)
                .collection("genres")
                .build();
    }

    @Bean
    public Step conversionGenreStep(ItemReader<GenreTable> readerGenre,
                                    ItemWriter<GenreDocument> writerGenre,
                                    ItemProcessor<GenreTable, GenreDocument> processorGenre) {
        return new StepBuilder(CONVERSION_GENRE_STEP_NAME, jobRepository)
                .<GenreTable, GenreDocument>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(readerGenre)
                .processor(processorGenre)
                .writer(writerGenre)
                .listener(new ItemReadListener<GenreTable>() {
                    @Override
                    public void beforeRead() {
                        logger.info("Start read genres from sqlDB");
                    }

                    @Override
                    public void afterRead(GenreTable genreTable) {
                        logger.info("End read. Genre is {}", genreTable.getName());
                    }

                    @Override
                    public void onReadError(Exception ex) {
                        logger.info("Error when we try to read genres from sqlDB");
                    }
                }).build();
    }
}
