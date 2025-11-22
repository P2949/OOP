package controllers;

import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.jetbrains.annotations.NotNull;


public class DatabaseController {

    //basic read and write lines for csv files
    public void writeAllLines(List<String[]> lines, @NotNull Path path) throws Exception {
        try (CSVWriter writer = new CSVWriter(new FileWriter(path.toString()))) {
            writer.writeAll(lines);
        }
    }
        public List<String[]> readAllLines(List<String[]> lines, @NotNull Path path) throws Exception {
            try (Reader reader = Files.newBufferedReader(path)) {
                try (CSVReader csvReader = new CSVReader(reader)) {
                    return csvReader.readAll();
                }
            }
        }
}
