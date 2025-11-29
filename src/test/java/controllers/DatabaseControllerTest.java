package controllers;

import models.CSVModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.*;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatabaseControllerTest {

  private DatabaseController db;
  private Path tempDir;

  @BeforeEach
  void setup() throws Exception {
    db = new DatabaseController();
    tempDir = Files.createTempDirectory("csvtest");
  }

  @AfterEach
  void cleanup() throws Exception {
    if (Files.exists(tempDir)) {
      Files.walk(tempDir)
          .sorted(Comparator.reverseOrder())
          .forEach(path -> path.toFile().delete());
    }
  }

  private Path path(String name) {
    return tempDir.resolve(name + ".csv");
  }

  @Test
  void writeAllLines_and_readAllLines_roundTrip() throws Exception {
    Path file = path("TestFile");

    List<String[]> expected = List.of(
        new String[]{"headerA", "headerB"},
        new String[]{"1", "one"},
        new String[]{"2", "two"}
    );

    db.writeAllLines(expected, file);

    List<String[]> result = db.readAllLines(file);

    assertEquals(expected.size(), result.size());
    assertArrayEquals(expected.get(0), result.get(0));
    assertArrayEquals(expected.get(1), result.get(1));
    assertArrayEquals(expected.get(2), result.get(2));
  }

  @Test
  void readAllLines_returnsEmptyList_whenFileDoesNotExist() throws Exception {
    Path file = path("Missing");

    List<String[]> result = db.readAllLines(file);

    assertTrue(result.isEmpty());
  }

  @Test
  void search_findsWordAndReturnsCorrectLineIndex() throws Exception {
    Path file = path("SearchTest");

    db.writeAllLines(List.of(
        new String[]{"headerA", "headerB"},
        new String[]{"sweets", "chocolate"},
        new String[]{"berries", "nuts"}
    ), file);

    assertEquals(2, db.Search(file, "sweets"));
    assertEquals(3, db.Search(file, "nuts"));
    assertEquals(-1, db.Search(file, "missing"));
  }

  @Test
  void ReadLine_returnsCorrectLine() throws Exception {
    Path file = path("LineTest");

    db.writeAllLines(List.of(
        new String[]{"A1", "A2"},
        new String[]{"A", "B"},
        new String[]{"C", "D"}
    ), file);

    assertArrayEquals(new String[]{"A", "B"}, db.ReadLine(file, 1));
    assertArrayEquals(new String[]{"C", "D"}, db.ReadLine(file, 2));
  }

  @Test
  void deleteLine_removesSpecificLine() throws Exception {
    Path file = path("DeleteTest");

    db.writeAllLines(List.of(
        new String[]{"A1", "A2"},
        new String[]{"A", "B"},
        new String[]{"C", "D"}
    ), file);

    db.deleteLine(file, 1);

    List<String[]> result = db.readAllLines(file);

    assertEquals(2, result.size());
    assertArrayEquals(new String[]{"A1", "A2"}, result.get(0));
    assertArrayEquals(new String[]{"C", "D"}, result.get(1));
  }

  @Test
  void hasMissingData_trueWhenMoreThanHeader() throws Exception {
    Path file = path("Room.csv");

    db.writeAllLines(List.of(
        new String[]{"header"},
        new String[]{"data"}
    ), file);

    assertTrue(db.hasMissingData("Room"));
  }

  @Test
  void hasMissingData_falseForMissingFile() throws Exception {
    assertFalse(db.hasMissingData("NonexistentEntity"));
  }

  @Test
  void loadAllFromCSV_removesHeader() throws Exception {
    Path file = path("Module.csv");

    db.writeAllLines(List.of(
        new String[]{"Header1", "Header2"},
        new String[]{"A", "B"},
        new String[]{"C", "D"}
    ), file);

    List<String[]> rows = db.loadAllFromCSV("Module");

    assertEquals(2, rows.size());
    assertArrayEquals(new String[]{"A", "B"}, rows.get(0));
    assertArrayEquals(new String[]{"C", "D"}, rows.get(1));
  }

  @Test
  void existsInCSV_detectsExistingPrimaryKey() throws Exception {
    Path file = path("Student.csv");

    db.writeAllLines(List.of(
        new String[]{"id", "name"},
        new String[]{"123", "John"}
    ), file);

    assertTrue(db.existsInCSV("Student", "123"));
    assertFalse(db.existsInCSV("Student", "456"));
  }

  @Test
  void loadFromCSVByKey_returnsCorrectRowOrNull() throws Exception {
    Path file = path("Program.csv");

    db.writeAllLines(List.of(
        new String[]{"id", "name"},
        new String[]{"CS", "Computer Science"},
        new String[]{"IT", "Something Interesting"}
    ), file);

    assertArrayEquals(
        new String[]{"CS", "Computer Science"},
        db.loadFromCSVByKey("Program", "CS")
    );

    assertNull(db.loadFromCSVByKey("Program", "Missing"));
  }

  @Test
  void save_writesNewModelAndHandlesDuplicate() throws Exception {
    CSVModel model = mock(CSVModel.class);
    when(model.getClass().getSimpleName()).thenReturn("Student");
    when(model.toCSVRow()).thenReturn(new String[]{"100", "John"});
    when(model.getCSVHeader()).thenReturn(new String[]{"id", "name"});

    Path file = path("Student.csv");

    db.save(model);
    List<String[]> first = db.readAllLines(file);
    assertEquals(2, first.size());

    when(model.toCSVRow()).thenReturn(new String[]{"100", "JohnUpdated"});
    db.save(model);

    List<String[]> second = db.readAllLines(file);
    assertEquals(2, second.size());
    assertArrayEquals(new String[]{"100", "JohnUpdated"}, second.get(1));
  }
}
