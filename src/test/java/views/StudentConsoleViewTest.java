package views;

import models.*;
import models.Module;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentConsoleViewTest {

  private StudentConsoleView view;
  private ByteArrayOutputStream out;
  private PrintStream originalOut;

  @BeforeEach
  void setup() {
    view = new StudentConsoleView();
    out = new ByteArrayOutputStream();
    originalOut = System.out;
    System.setOut(new PrintStream(out));
  }

  @AfterEach
  void cleanup() {
    System.setOut(originalOut);
  }

  private String output() {
    return out.toString();
  }

  @Test
  void displayTimeTable_noGroups_printsNoTimetableMessage() {
    Student student = mock(Student.class);
    when(student.getName()).thenReturn("John");
    when(student.getStudentID()).thenReturn(123);
    when(student.getProgram()).thenReturn(null);
    when(student.getYearOfStudy()).thenReturn(1);
    when(student.getGroups()).thenReturn(List.of());

    view.displayTimeTable(student);
    String out = output();

    assertTrue(out.contains("Timetable for John"));
    assertTrue(out.contains("Student ID: 123"));
    assertTrue(out.contains("Course: None"));
    assertTrue(out.contains("No timetable entries found"));
  }

  @Test
  void displayTimeTable_categorizesSessionsCorrectly() {
    Student student = mock(Student.class);

    Lecture lecture = mock(Lecture.class);
    Tutorial tutorial = mock(Tutorial.class);
    Laboratory lab = mock(Laboratory.class);

    Group gl = mock(Group.class);
    Group gt = mock(Group.class);
    Group gb = mock(Group.class);

    when(gl.getSession()).thenReturn(lecture);
    when(gt.getSession()).thenReturn(tutorial);
    when(gb.getSession()).thenReturn(lab);

    when(student.getName()).thenReturn("Jane");
    when(student.getStudentID()).thenReturn(456);
    when(student.getProgram()).thenReturn(null);
    when(student.getYearOfStudy()).thenReturn(2);
    when(student.getGroups()).thenReturn(List.of(gl, gt, gb));

    setupMockSession(lecture, "A123");
    setupMockSession(tutorial, "B123");
    setupMockSession(lab, "C123");

    view.displayTimeTable(student);

    String out = output();

    assertTrue(out.contains("Lectures"));
    assertTrue(out.contains("Tutorials"));
    assertTrue(out.contains("Laboratories"));
    assertTrue(out.contains("A123"));
    assertTrue(out.contains("B123"));
    assertTrue(out.contains("C123"));
  }

  @Test
  void displayModules_noModules_printsMessage() {
    view.displayModules(List.of());
    assertTrue(output().contains("No modules found"));
  }

  @Test
  void displayModules_printsModules() {
    Module m = mock(Module.class);
    Lecturer lec = mock(Lecturer.class);

    when(m.getModuleCode()).thenReturn("D123");
    when(m.getModuleName()).thenReturn("Dancing");
    when(m.getLecturer()).thenReturn(lec);
    when(lec.getName()).thenReturn("Dr. Who");
    when(m.getLengthInWeeks()).thenReturn(12);
    when(m.getStartWeek()).thenReturn(1);

    view.displayModules(List.of(m));

    String out = output();
    assertTrue(out.contains("D123"));
    assertTrue(out.contains("Dancing"));
    assertTrue(out.contains("Dr. Who"));
  }

  @Test
  void displayLectures_emptyList_printsNothing() {
    view.displayLectures(List.of());
    assertEquals("", output().trim());
  }

  @Test
  void displayLectures_printsLectureDetails() {
    Lecture lec = mock(Lecture.class);
    setupMockSession(lec, "E123");

    view.displayLectures(List.of(lec));
    String out = output();

    assertTrue(out.contains("E123"));
    assertTrue(out.contains("MONDAY"));
    assertTrue(out.contains("09:00 - 10:00"));
  }

  @Test
  void displayTutorials_printsTutorialDetails() {
    Tutorial t = mock(Tutorial.class);
    setupMockSession(t, "F123");

    view.displayTutorials(List.of(t));
    assertTrue(output().contains("F123"));
  }

  @Test
  void displayLaboratories_printsLabDetails() {
    Laboratory lab = mock(Laboratory.class);
    setupMockSession(lab, "G123");

    view.displayLaboratories(List.of(lab));
    assertTrue(output().contains("G123"));
  }

  @Test
  void showMessage_printsCorrectly() {
    view.showMessage("Hello there!");
    assertTrue(output().contains("Hello there!"));
  }

  private void setupMockSession(Session session, String moduleCode) {
    Module m = mock(Module.class);
    Room r = mock(Room.class);

    when(session.getModule()).thenReturn(m);
    when(session.getDay()).thenReturn(Session.Day.MONDAY);
    when(session.getStartTime()).thenReturn(LocalTime.of(9, 0));
    when(session.getEndTime()).thenReturn(LocalTime.of(10, 0));
    when(session.getStartWeek()).thenReturn(1);
    when(session.getEndWeek()).thenReturn(12);

    when(session.getRoom()).thenReturn(r);
    when(r.getRoomNumber()).thenReturn("A123");
    when(m.getModuleCode()).thenReturn(moduleCode);
  }
}
