package controllers;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import models.CSVModel;
import org.jetbrains.annotations.NotNull;

import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class DatabaseController {

    //basic read and write lines for csv files
    public void writeAllLines(List<String[]> lines, @NotNull Path path) throws Exception {
        try (CSVWriter writer = new CSVWriter(new FileWriter(path.toString()))) {
            writer.writeAll(lines);
        }
    }

    public List<String[]> readAllLines(@NotNull Path path) throws Exception {
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        try (Reader reader = Files.newBufferedReader(path)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
            }
        }
    }

    //search for a word in a csv file
    public int Search(@NotNull Path path, String search) throws Exception {
        List<String[]> lines = readAllLines(path);
        int n = 0;
        for (String[] line : lines) {
            n++;
            for (String word : line) {
                if (word.equals(search)) {
                    return n;
                }
            }
        }
        return -1;
    }

    // Read a specific line from a csv file
    public String[] ReadLine(@NotNull Path path, int line) throws Exception {
        List<String[]> lines = readAllLines(path);
        return lines.get(line - 1);
    }


    public void save(CSVModel model) throws Exception {
        String className = model.getClass().getSimpleName();
        Path path = Paths.get("./csv/" + className + ".csv");
        String[] row = model.toCSVRow();
        List<String[]> lines = readAllLines(path);
        
        if (lines.isEmpty()) {
            String[] header = model.getCSVHeader();
            lines.add(header);
        }
        
        lines.add(row);
        writeAllLines(lines, path);
    }
}
