package ru.otus.marchenko.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.*;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.marchenko.models.Tomato;
import ru.otus.marchenko.services.RipeningService;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannelSpec<?, ?> greenChannel() {
        return MessageChannels.queue(10);
    }

    @Bean
    public MessageChannelSpec<?, ?> redChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2);
    }

    @Bean
    public IntegrationFlow plantFlow(RipeningService ripeningService) {
        return IntegrationFlow.from(greenChannel())
                .split()
                .handle(ripeningService, "ripen")
                .<Tomato, Tomato>transform(tomato -> new Tomato(tomato.getName(), tomato.getRipe(), tomato.getSize()))
                .aggregate()
                .channel(redChannel())
                .get();
    }
}
