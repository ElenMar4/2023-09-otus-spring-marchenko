package ru.otus.marchenko.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.marchenko.services.cleanCacheStepService;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class JobConfig {

    @Autowired
    private final JobRepository jobRepository;

    @Autowired
    private final PlatformTransactionManager platformTransactionManager;

    @Autowired
    private cleanCacheStepService cleanCacheStepService;

    public static final String IMPORT_JOB_NAME = "importJob";

    @Bean
    public Job importJob(Step conversionAuthorStep,
                             Step conversionGenreStep,
                             Step conversionBookStep,
                             Step conversionCommentStep,
                             Step cleanCacheStep
    ) {
        return new JobBuilder(IMPORT_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(conversionAuthorStep)
                .next(conversionGenreStep)
                .next(conversionBookStep)
                .next(conversionCommentStep)
                .next(cleanCacheStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        log.info("Start convertionJob");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        log.info("End convertionJob");
                    }
                })
                .build();
    }

    @Bean
    public MethodInvokingTaskletAdapter cleanCacheStepTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(cleanCacheStepService);
        adapter.setTargetMethod("cleanCache");

        return adapter;
    }

    @Bean
    public Step cleanCacheStep() {
        return new StepBuilder("cleanCacheStep", jobRepository)
                .tasklet(cleanCacheStepTasklet(), platformTransactionManager)
                .build();
    }
}
