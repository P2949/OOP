package views;

import models.Program;
import models.Student;
import models.Group;
import models.Module;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class AdminConsoleViewTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final InputStream originalIn = System.in;

  private void provideInput(String data) {
    System.setIn(new ByteArrayInputStream(data.getBytes()));
  }

  private void captureOutput() {
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  void restoreSystemIO() {
    System.setOut(originalOut);
    System.setIn(originalIn);
  }

  @Test
  void showMenu_readsIntegerOption() {
    provideInput("4\n");
    AdminConsoleView view = new AdminConsoleView();
    int result = view.showMenu();
    assertEquals(4, result);
  }

  @Test
  void addStudent_createsStudentCorrectly() {
    captureOutput();
    provideInput(
        "\nJohn Doe\n" +
            "21\n" +
            "2\n"
    );

    AdminConsoleView view = new AdminConsoleView();
    Student s = view.addStudent();

    assertEquals("John Doe", s.getName());
    assertEquals(21, s.getAge());
    assertEquals(2, s.getYearOfStudy());
    assertEquals("Temp", s.getProgram().getProgramName());
  }

  @Test
  void selectProgram_selectsCorrectProgramByIndex() {
    captureOutput();
    provideInput("2\n");

    Program p1 = new Program("A", 1);
    Program p2 = new Program("B", 2);

    AdminConsoleView view = new AdminConsoleView();
    String selected = view.selectProgram(java.util.List.of(p1, p2));

    assertEquals("B", selected);
  }

  @Test
  void selectProgram_returnsNullOnInvalidChoice() {
    captureOutput();
    provideInput("9\n");

    Program p1 = new Program("A", 1);

    AdminConsoleView view = new AdminConsoleView();
    String selected = view.selectProgram(java.util.List.of(p1));

    assertNull(selected);
  }

  @Test
  void selectStudent_returnsStudentIdCorrectly() {
    captureOutput();
    provideInput("1\n");

    Program p = new Program("Temp", 0);
    Student s1 = new Student("John", 20, p, 1);
    Student s2 = new Student("Jane", 22, p, 2);

    AdminConsoleView view = new AdminConsoleView();
    int id = view.selectStudent(java.util.List.of(s1, s2));

    assertEquals(s1.getStudentID(), id);
  }

  @Test
  void selectStudent_returnsMinusOneForInvalidIndex() {
    captureOutput();
    provideInput("5\n");

    Program p = new Program("Temp", 0);
    Student s = new Student("John", 20, p, 1);

    AdminConsoleView view = new AdminConsoleView();
    int id = view.selectStudent(java.util.List.of(s));

    assertEquals(-1, id);
  }

  @Test
  void showMessage_printsMessage() {
    captureOutput();

    AdminConsoleView view = new AdminConsoleView();
    view.showMessage("Hello there!");

    assertTrue(outContent.toString().contains("Hello!"));
  }

  @Test
  void createCourse_createsCompleteProgramWithModulesAndSessions() {
    captureOutput();

    provideInput(
        "\nHobby horsing\n" + // Course name
            "5\n" +                  // module count
            "AB987\n" +              // module code
            "Something Interesting\n" +        // module name
            "Dr. Who\n" +          // lecturer name
            "45\n" +                 // lecturer age
            "SID123\n" +               // lecturer ID
            "Science & Engineering\n" +          // department
            "12\n" +                 // module length
            "1\n" +                  // start week
            "1\n" +                  // session count
            "lecture\n" +            // session type
            "R12\n" +                // room number
            "50\n" +                 // capacity
            "Main Building\n" +       // building
            "MONDAY\n" +             // day
            "1\n" +                  // session start week
            "12\n" +                 // end week
            "09:00\n" +              // start time
            "11:00\n"                // end time
    );

    AdminConsoleView view = new AdminConsoleView();
    Program program = view.createCourse();

    assertEquals("Hobby horsing", program.getProgramName());
    assertEquals(1, program.getModulesTaught().size());

    Module m = program.getModulesTaught().get(0);
    assertEquals("AB987", m.getModuleCode());
    assertEquals("Something Interesting", m.getModuleName());

    assertEquals(1, m.getSessions().size());
    Group g = m.getSessions().get(0);
    assertEquals('A', g.getGroupID());
  }
}
