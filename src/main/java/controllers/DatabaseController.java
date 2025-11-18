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

    //for now just testing how opencsv works, seems good and simple
    public static List<String[]> writeAllLines(List<String[]> lines, @NotNull Path path) throws Exception {
        try (CSVWriter writer = new CSVWriter(new FileWriter(path.toString()))) {
            writer.writeAll(lines);
        }
        try (Reader reader = Files.newBufferedReader(path)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
            }
        }
    }
    public static void main(String[] args) throws Exception {
        List<String[]> a = new ArrayList<>();
        String[] b = new String[2];
        b[0] = "aaa";
        b[1] = "bbb";
        a.add(b);
        List<String[]> c = writeAllLines(a, Path.of("./csv/a.csv"));
        String d = Arrays.toString(c.getFirst());
        System.out.println(d);
    }
}
