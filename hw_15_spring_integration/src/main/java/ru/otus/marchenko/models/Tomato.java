package ru.otus.marchenko.models;

import lombok.Data;

@Data
public class Tomato {
    private String name;
    private Boolean ripe = false;
    private String size;

    public Tomato(String name) {
        this.name = name;
    }

    public Tomato(String name, Boolean ripe, String size) {
        this.name = name;
        this.ripe = ripe;
        this.size = size;
    }
}
