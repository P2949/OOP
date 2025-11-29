package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentTest {

  private Program program;
  private Module module;
  private Group group;
  private Session session;
  private Room room;

  @BeforeEach
  void setup() throws Exception {
    Field counter = Student.class.getDeclaredField("studentCounter");
    counter.setAccessible(true);
    counter.setInt(null, 0);

    program = mock(Program.class);
    module = mock(Module.class);
    group = mock(Group.class);
    session = mock(Session.class);
    room = mock(Room.class);

    when(group.getSession()).thenReturn(session);
    when(session.getModule()).thenReturn(module);
    when(session.getDay()).thenReturn(Session.Day.MONDAY);
    when(session.getStartTime()).thenReturn(LocalTime.of(9, 0));
    when(session.getRoom()).thenReturn(room);
    when(room.getRoomNumber()).thenReturn("A123");
    when(session.getClass().getSimpleName()).thenReturn("Lecture");
    when(module.getModuleCode()).thenReturn("CS123");
  }

  @Test
  void constructor_initializesFieldsCorrectly() {
    when(program.getModulesTaught()).thenReturn(List.of(module));

    Student s = new Student("John", 20, program, 1);

    assertEquals("John", s.getName());
    assertEquals(20, s.getAge());
    assertEquals(1, s.getYearOfStudy());
    assertEquals(program, s.getProgram());
    assertTrue(s.getGroups().isEmpty());
    assertEquals(LocalDate.now().getYear() + 1, s.getStudentID());
  }

  @Test
  void studentCounter_incrementsPerStudent() {
    when(program.getModulesTaught()).thenReturn(List.of(module));

    Student s1 = new Student("A", 18, program, 1);
    Student s2 = new Student("B", 19, program, 1);

    assertEquals(s1.getStudentID() + 1, s2.getStudentID());
    assertEquals(2, s2.getStudentCount());
  }

  @Test
  void setProgram_throwsIfProgramHasNoModules() {
    when(program.getModulesTaught()).thenReturn(List.of());

    Student s = new Student("John", 20, program, 1);

    Program p2 = mock(Program.class);
    when(p2.getModulesTaught()).thenReturn(List.of());

    assertThrows(IllegalArgumentException.class, () -> s.setProgram(p2));
  }

  @Test
  void setProgram_throwsIfStudentNotEnrolledInAllModules() {
    when(program.getModulesTaught()).thenReturn(List.of(module));

    Student s = new Student("John", 20, program, 1);

    Program p2 = mock(Program.class);
    when(p2.getModulesTaught()).thenReturn(List.of(module));

    assertThrows(IllegalArgumentException.class, () -> s.setProgram(p2));
  }

  @Test
  void setProgram_allowsSettingWhenStudentEnrolledInAllModules() {
    when(program.getModulesTaught()).thenReturn(List.of(module));

    Student s = new Student("John", 20, program, 1);

    when(module.getEnrolledStudents()).thenReturn(List.of(s));
    when(group.getGroupID()).thenReturn('A');
    s.addGroup(group);

    Program p2 = mock(Program.class);
    when(p2.getModulesTaught()).thenReturn(List.of(module));

    assertDoesNotThrow(() -> s.setProgram(p2));
    assertEquals(p2, s.getProgram());
  }

  @Test
  void addGroup_throwsIfStudentNotEnrolledInModule() {
    when(module.getEnrolledStudents()).thenReturn(List.of());

    Student s = new Student("John", 20, program, 1);

    assertThrows(IllegalArgumentException.class, () -> s.addGroup(group));
  }

  @Test
  void addGroup_addsGroupIfValid() {
    when(program.getModulesTaught()).thenReturn(List.of(module));
    Student s = new Student("John", 20, program, 1);

    when(module.getEnrolledStudents()).thenReturn(List.of(s));
    when(group.getGroupID()).thenReturn('A');

    s.addGroup(group);
    assertTrue(s.getGroups().contains(group));
  }

  @Test
  void addGroup_allowsIdempotentSameGroup() {
    when(program.getModulesTaught()).thenReturn(List.of(module));
    Student s = new Student("John", 20, program, 1);

    when(module.getEnrolledStudents()).thenReturn(List.of(s));
    when(group.getGroupID()).thenReturn('A');

    s.addGroup(group);
    s.addGroup(group);
    assertEquals(1, s.getGroups().size());
  }

  @Test
  void removeGroup_removesFromGroups() {
    when(program.getModulesTaught()).thenReturn(List.of(module));
    Student s = new Student("John", 20, program, 1);

    when(module.getEnrolledStudents()).thenReturn(List.of(s));
    when(group.getGroupID()).thenReturn('A');

    s.addGroup(group);
    when(group.getStudents()).thenReturn(List.of(s));
    s.removeGroup(group);

    assertFalse(s.getGroups().contains(group));
  }

  @Test
  void clearGroups_emptiesList() {
    when(program.getModulesTaught()).thenReturn(List.of(module));
    Student s = new Student("John", 20, program, 1);

    when(module.getEnrolledStudents()).thenReturn(List.of(s));
    when(group.getGroupID()).thenReturn('A');

    s.addGroup(group);
    assertFalse(s.getGroups().isEmpty());

    s.clearGroups();
    assertTrue(s.getGroups().isEmpty());
  }

  @Test
  void toCSVRow_outputsCorrectValues() {
    when(program.getProgramName()).thenReturn("BSc Something");
    when(program.getModulesTaught()).thenReturn(List.of(module));
    Student s = new Student("John", 20, program, 1);

    when(module.getEnrolledStudents()).thenReturn(List.of(s));
    when(group.getGroupID()).thenReturn('A');
    s.addGroup(group);

    String[] csv = s.toCSVRow();

    String expectedGroup =
        "A:" +
            session.getClass().getSimpleName() +
            ":CS101:MONDAY:09:00:A101";

    assertEquals(String.valueOf(s.getStudentID()), csv[0]);
    assertEquals("John", csv[1]);
    assertEquals("20", csv[2]);
    assertEquals("BSc Something", csv[3]);
    assertEquals("1", csv[4]);
    assertTrue(csv[5].contains(expectedGroup));
  }

  @Test
  void getCSVHeader_isCorrect() {
    when(program.getModulesTaught()).thenReturn(List.of(module));
    Student s = new Student("X", 20, program, 1);

    assertArrayEquals(
        new String[]{"studentID", "name", "age", "programName", "yearOfStudy", "groups"},
        s.getCSVHeader()
    );
  }
}
