package controllers;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import models.CSVModel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Database controller.
 */
public class DatabaseController {

    /**
     * Write all lines.
     *
     * @param lines the lines
     * @param path  the path
     * @throws Exception the exception
     */
//basic read and write lines for csv files
    public void writeAllLines(List<String[]> lines, @NotNull Path path) throws Exception {
        try (CSVWriter writer = new CSVWriter(new FileWriter(path.toString()))) {
            writer.writeAll(lines);
        }
    }

    /**
     * Read all lines list.
     *
     * @param path the path
     * @return the list
     * @throws Exception the exception
     */
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

    /**
     * Search int.
     *
     * @param path   the path
     * @param search the search
     * @return the int
     * @throws Exception the exception
     */
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

    /**
     * Read line string [ ].
     *
     * @param path the path
     * @param line the line
     * @return the string [ ]
     * @throws Exception the exception
     */
// Read a specific line from a csv file
    public String[] ReadLine(@NotNull Path path, int line) throws Exception {
        List<String[]> lines = readAllLines(path);
        return lines.get(line - 1);
    }


    /**
     * Save.
     *
     * @param model the model
     * @throws Exception the exception
     */
    public void save(@NotNull CSVModel model) throws Exception {
        String className = model.getClass().getSimpleName();
        Path path = Paths.get("./csv/" + className + ".csv");
        String[] row = model.toCSVRow();
        List<String[]> lines = readAllLines(path);

        if (lines.isEmpty()) {
            String[] header = model.getCSVHeader();
            lines.add(header);
        }

        // Check for duplicates using an entity-specific key
        //  Currently, the default action is to overwrite because it works nicely with everything else
        if (isDuplicate(lines, row, className)) {
            IO.println("Duplicate " + className + " Overwriting (harmless?)");
            int existingIndex = findDuplicateIndex(lines, row, className);
            if (existingIndex != -1) {
                lines.set(existingIndex, row);
            } else {
                IO.println("Duplicate " + className + " not found");
                lines.add(row); // if this happens, something is wrong, very wrong
            }
        } else {
            lines.add(row);
        }
        writeAllLines(lines, path);
    }

    /**
     * Check for duplicates based on entity-specific primary keys.
     *
     * @param lines      all existing lines
     * @param newRow     the row to check
     * @param entityType the entity class name
     * @return true if duplicate exists
     */
    private boolean isDuplicate(@NotNull List<String[]> lines, String[] newRow, String entityType) {
        if (lines.size() <= 1 || newRow.length == 0) {
            return false;
        }

        // Determine which columns form the primary key for each entity
        int[] keyColumns = getPrimaryKeyColumns(entityType);

        // Start from index 1 to skip the header
        for (int i = 1; i < lines.size(); i++) {
            String[] existingRow = lines.get(i);
            if (rowsMatchOnKey(existingRow, newRow, keyColumns)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get primary key column indices for each entity type.
     * <p>
     * if the primary key for stuff changes, you need to change stuff here.
     */
    @Contract(pure = true)
    private int @NotNull [] getPrimaryKeyColumns(@NotNull String entityType) {
        return switch (entityType) {
            case "Student" -> new int[]{0}; // studentID
            case "Lecturer" -> new int[]{0}; // staffID
            case "Module" -> new int[]{0}; // moduleCode
            case "Program" -> new int[]{0}; // programName
            case "Room" -> new int[]{0}; // roomNumber
            case "Group" -> new int[]{0, 1}; // groupID + session
            case "Lecture", "Laboratory", "Tutorial" -> new int[]{1, 3, 4, 6}; // moduleCode, roomNumber, day, startTime
            default -> new int[]{0};
        };
    }

    /**
     * Check if two rows match on the specified key columns.
     */
    @Contract(pure = true)
    private boolean rowsMatchOnKey(String[] row1, String[] row2, int @NotNull [] keyColumns) {
        for (int col : keyColumns) {
            if (col >= row1.length || col >= row2.length) {
                return false;
            }
            if (!row1[col].equals(row2[col])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Find the index of a duplicate row.
     *
     * @return index of duplicate, or -1 if not found
     */
    private int findDuplicateIndex(@NotNull List<String[]> lines, String[] newRow, String entityType) {
        if (lines.size() <= 1 || newRow.length == 0) {
            return -1;
        }

        int[] keyColumns = getPrimaryKeyColumns(entityType);

        for (int i = 1; i < lines.size(); i++) {
            if (rowsMatchOnKey(lines.get(i), newRow, keyColumns)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Delete a specific line from a CSV file by its index.
     *
     * @param path      the path to the CSV file
     * @param lineIndex the index of the line to delete (0-based, where 0 is the header)
     * @throws Exception if the file doesn't exist or the index is out of bounds
     */
    public void deleteLine(@NotNull Path path, int lineIndex) throws Exception {
        List<String[]> lines = readAllLines(path);

        if (lines.isEmpty()) {
            IO.println("Cannot delete from empty file: " + path);
        }

        if (lineIndex < 0 || lineIndex >= lines.size()) {
            IO.println(
                    "Line index " + lineIndex + " is out of bounds. File has " + lines.size() + " lines."
            );
        }

        if (lineIndex == 0) {
            IO.println("Cannot delete header row (index 0)");
        }

        lines.remove(lineIndex);
        writeAllLines(lines, path);
    }
}
