package ru.otus.marchenko.dao.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader implements ResourceReading {
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> readTextResourceForLine() {
        List<String> listLine = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getFileFromResourceAsStream(fileName)))) {
            while (reader.ready()) {
                listLine.add(reader.readLine());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return listLine;
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }
}
