package ru.otus.marchenko.services;

public class ConsoleIoService implements IoService {
    @Override
    public void println(String line) {
        System.out.println(line);
    }

    @Override
    public String readLine() {
        return null;
    }
}
