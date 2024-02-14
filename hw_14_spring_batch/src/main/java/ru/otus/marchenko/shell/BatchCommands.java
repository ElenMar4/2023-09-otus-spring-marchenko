package ru.otus.marchenko.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final Job convertionJob;

    private final JobLauncher jobLauncher;

    @ShellMethod(value = "startMigrationJobWithJobLauncher", key = "c")
    public String startMigrationJobWithJobLauncher() throws Exception {
        JobExecution execution = jobLauncher.run(convertionJob, new JobParametersBuilder()
                .addLocalDateTime("time", LocalDateTime.now())
                .toJobParameters());
        return "OK";
    }
}
