package ru.otus.marchenko.service;

public interface LocalizedMessagesService {
    String getMessage(String code, Object ...args);
}
