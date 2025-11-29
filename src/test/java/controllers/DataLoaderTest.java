package controllers;

import models.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DataLoaderTest {

  @Test
  void loadAllData_populatesAllCollectionsFromCsv() throws Exception {
    DatabaseController db = mock(DatabaseController.class);

    when(db.loadAllFromCSV(anyString())).thenAnswer(invocation -> {
      String type = invocation.getArgument(0, String.class);
      return switch (type) {
        case "Room" -> List.of(
            new String[]{"A101", "30", "Lecture Room", "Main", "false"}
        );
        case "Lecturer" -> List.of(
            new String[]{"L1", "Dr Who", "45", "CS"}
        );
        case "Module" -> List.of(
            new String[]{"CS101", "Interesting Module", "L1", "12", "1"}
        );
        case "Program" -> List.of(
            new String[]{"BSc CS", "1", "CS101"}
        );
        case "Lecture" -> List.of(
            new String[]{"1", "CS101", "L1", "R101", "MONDAY", "1", "09:00", "10:00", "12"}
        );
        case "Laboratory", "Tutorial" -> List.of();
        case "Group" -> List.of(
            new String[]{"A", "Lecture:CS101:MONDAY:09:00:R101"}
        );
        case "Student" -> List.of(
            new String[]{"123", "John", "20", "BSc CS", "1",
                "A:Lecture:CS101:MONDAY:09:00:A101"}
        );
        default -> List.of();
      };
    });

    DataLoader loader = new DataLoader(db);
    loader.loadAllData();

    assertEquals(1, loader.getRooms().size());
    assertEquals(1, loader.getLecturers().size());
    assertEquals(1, loader.getModules().size());
    assertEquals(1, loader.getPrograms().size());
    assertEquals(1, loader.getSessions().size());
    assertEquals(1, loader.getGroups().size());
    assertEquals(1, loader.getStudents().size());

    Collection<Student> students = loader.getStudents();
    assertFalse(students.isEmpty());
  }

  @Test
  void loadAllData_withEmptyCSVs_resultsInEmptyCollections() throws Exception {
    DatabaseController db = mock(DatabaseController.class);
    when(db.loadAllFromCSV(anyString())).thenReturn(List.of());

    DataLoader loader = new DataLoader(db);
    loader.loadAllData();

    assertTrue(loader.getRooms().isEmpty());
    assertTrue(loader.getLecturers().isEmpty());
    assertTrue(loader.getModules().isEmpty());
    assertTrue(loader.getPrograms().isEmpty());
    assertTrue(loader.getSessions().isEmpty());
    assertTrue(loader.getGroups().isEmpty());
    assertTrue(loader.getStudents().isEmpty());
  }
}
