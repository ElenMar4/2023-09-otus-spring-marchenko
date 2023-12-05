package ru.otus.marchenko.commands;

import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.sql.SQLException;

@ShellComponent
public class H2Commands {

    @ShellMethod(value = "Show console", key = "h2")
    public void showH2Console() throws SQLException {
        Console.main();
    }
}
