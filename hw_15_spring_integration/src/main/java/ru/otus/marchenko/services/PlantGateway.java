package ru.otus.marchenko.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.marchenko.models.Tomato;

import java.util.Collection;

@MessagingGateway
public interface PlantGateway {
    @Gateway(requestChannel = "greenChannel", replyChannel = "redChannel")
    Collection<Tomato> process(Collection<Tomato> tomatoes);
}
