package ru.otus.marchenko.config.steps;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.marchenko.models.noSqlModels.CommentDocument;
import ru.otus.marchenko.models.sqlModels.CommentTable;
import ru.otus.marchenko.services.ConversionService;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CommentsStepConfig {

    @Autowired
    private final JobRepository jobRepository;

    @Autowired
    private final PlatformTransactionManager platformTransactionManager;

    private static final int CHUNK_SIZE = 5;
    public static final String JPA_COMMENT_ITEM_READER_NAME = "jpaCommentItemReader";
    public static final String CONVERSION_COMMENT_STEP_NAME = "conversionCommentStep";

    @Bean
    public JpaCursorItemReader<CommentTable> readerComment(EntityManagerFactory factory) {
        return new JpaCursorItemReaderBuilder<CommentTable>()
                .name(JPA_COMMENT_ITEM_READER_NAME)
                .entityManagerFactory(factory)
                .saveState(true)
                .queryString("select c from CommentTable c")
                .build();
    }

    @Bean
    public ItemProcessor<CommentTable, CommentDocument> processorComment(ConversionService conversionService) {
        return conversionService::convertComment;
    }

    @Bean
    public MongoItemWriter<CommentDocument> writerComment(MongoTemplate template) {
        return new MongoItemWriterBuilder<CommentDocument>()
                .template(template)
                .collection("comments")
                .build();
    }

    @Bean
    public Step conversionCommentStep(JpaCursorItemReader<CommentTable> readerComment,
                                   MongoItemWriter<CommentDocument> writerComment,
                                   ItemProcessor<CommentTable, CommentDocument> processorComment) {
        return new StepBuilder(CONVERSION_COMMENT_STEP_NAME, jobRepository)
                .<CommentTable, CommentDocument>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(readerComment)
                .processor(processorComment)
                .writer(writerComment)
                .listener(new ItemReadListener<CommentTable>() {
                    @Override
                    public void beforeRead() {
                        log.info("Start read comments from sqlDB");
                    }

                    @Override
                    public void afterRead(CommentTable commentTable) {
                        log.info("End read. Comment is {}", commentTable.getMessage());
                    }

                    @Override
                    public void onReadError(Exception ex) {
                        log.info("Error when we try to read comments from sqlDB");
                    }
                }).build();
    }
}

