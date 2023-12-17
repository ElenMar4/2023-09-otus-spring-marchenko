package ru.otus.marchenko.repositories;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.marchenko.config", "ru.otus.marchenko.repositories"})
public class AbstractRepositoryTest {
}
