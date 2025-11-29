package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ModuleTest {

  private Lecturer lecturer;
  private Module module;

  @BeforeEach
  void setup() {
    lecturer = mock(Lecturer.class);
    module = new Module("CS123", "Interesting Module", lecturer, 12, 1);
  }

  @Test
  void constructorRegistersModuleWithLecturer() {
    verify(lecturer).addModuleTaught(module);
  }

  @Test
  void gettersReturnInitialValues() {
    assertEquals("CS123", module.getModuleCode());
    assertEquals("Interesting Module", module.getModuleName());
    assertEquals(12, module.getLengthInWeeks());
    assertEquals(1, module.getStartWeek());
    assertEquals(lecturer, module.getLecturer());
  }

  @Test
  void settersUpdateFields() {
    module.setModuleCode("CS456");
    module.setModuleName("Fun");
    module.setLengthInWeeks(15);
    module.setStartWeek(2);

    assertEquals("CS456", module.getModuleCode());
    assertEquals("Fun", module.getModuleName());
    assertEquals(15, module.getLengthInWeeks());
    assertEquals(2, module.getStartWeek());
  }

  @Test
  void setLecturer_updatesTeacherCorrectly() {
    Lecturer newLecturer = mock(Lecturer.class);

    module.setLecturer(newLecturer);

    verify(lecturer).removeModuleTaught(module);
    verify(newLecturer).addModuleTaught(module);
  }

  @Test
  void setLecturer_acceptsNull() {
    module.setLecturer(null);

    verify(lecturer).removeModuleTaught(module);
    assertNull(module.getLecturer());
  }

  @Test
  void getEnrolledStudents_returnsDefensiveCopy() {
    Student s = mock(Student.class);
    module.addEnrolledStudent(s);

    List<Student> list1 = module.getEnrolledStudents();
    List<Student> list2 = module.getEnrolledStudents();

    assertNotSame(list1, list2);
    assertEquals(1, list1.size());
  }

  @Test
  void addAndRemoveStudents_workCorrectly() {
    Student s1 = mock(Student.class);
    Student s2 = mock(Student.class);

    module.addEnrolledStudent(s1);
    module.addEnrolledStudent(s2);

    assertTrue(module.getEnrolledStudents().contains(s1));
    assertTrue(module.getEnrolledStudents().contains(s2));

    module.removeEnrolledStudent(s1);

    assertFalse(module.getEnrolledStudents().contains(s1));
    assertTrue(module.getEnrolledStudents().contains(s2));
  }

  private Group mockGroup(Module owner, Lecturer lec) {
    Group g = mock(Group.class);
    Session s = mock(Session.class);

    when(g.getSession()).thenReturn(s);
    when(s.getModule()).thenReturn(owner);
    when(s.getLecturer()).thenReturn(lec);

    return g;
  }

  @Test
  void setSessions_acceptsValidGroups() {
    Group g1 = mockGroup(module, lecturer);
    Group g2 = mockGroup(module, lecturer);

    module.setSessions(List.of(g1, g2));

    assertEquals(2, module.getSessions().size());
  }

  @Test
  void setSessions_rejectsGroupsBelongingToDifferentModule() {
    Lecturer lec = mock(Lecturer.class);
    Module otherModule = new Module("X", "Bad", lec, 5, 1);

    Group g = mockGroup(otherModule, lecturer);

    assertThrows(IllegalArgumentException.class,
        () -> module.setSessions(List.of(g)));
  }

  @Test
  void setSessions_rejectsGroupsWithWrongLecturer() {
    Lecturer wrongLecturer = mock(Lecturer.class);
    Group g = mockGroup(module, wrongLecturer);

    assertThrows(IllegalArgumentException.class,
        () -> module.setSessions(List.of(g)));
  }

  @Test
  void addSession_acceptsValidGroup() {
    Group g = mockGroup(module, lecturer);

    module.addSession(g);

    assertTrue(module.getSessions().contains(g));
  }

  @Test
  void addSession_rejectsInvalidModule() {
    Lecturer lec = mock(Lecturer.class);
    Module otherModule = new Module("Y", "Bad", lec, 10, 2);

    Group g = mockGroup(otherModule, lecturer);

    assertThrows(IllegalArgumentException.class,
        () -> module.addSession(g));
  }

  @Test
  void addSession_rejectsInvalidLecturer() {
    Lecturer wrongLecturer = mock(Lecturer.class);
    Group g = mockGroup(module, wrongLecturer);

    assertThrows(IllegalArgumentException.class,
        () -> module.addSession(g));
  }

  @Test
  void removeSession_removesCorrectly() {
    Group g = mockGroup(module, lecturer);
    module.addSession(g);

    module.removeSession(g);
    assertFalse(module.getSessions().contains(g));
  }

  @Test
  void toCSVRow_outputsCorrectFields() {
    Group g1 = mock(Group.class);
    Group g2 = mock(Group.class);
    Session s1 = mock(Session.class);
    Session s2 = mock(Session.class);

    when(g1.getGroupID()).thenReturn('A');
    when(g2.getGroupID()).thenReturn('B');
    when(g1.getSession()).thenReturn(s1);
    when(g2.getSession()).thenReturn(s2);
    when(s1.getModule()).thenReturn(module);
    when(s2.getModule()).thenReturn(module);
    when(s1.getModule().getModuleCode()).thenReturn("CS123");
    when(s2.getModule().getModuleCode()).thenReturn("CS123");

    module.setSessions(List.of(g1, g2));

    Student st1 = mock(Student.class);
    Student st2 = mock(Student.class);
    when(st1.getStudentID()).thenReturn(10);
    when(st2.getStudentID()).thenReturn(20);

    module.setEnrolledStudents(List.of(st1, st2));

    String[] csv = module.toCSVRow();

    assertArrayEquals(
        new String[]{
            "CS123",
            "Something Interesting",
            lecturer.getStaffID(),
            "12",
            "1",
            "CS123:A,CS123:B",
            "10,20"
        },
        csv
    );
  }

  @Test
  void CSVHeader_isCorrect() {
    assertArrayEquals(
        new String[]{
            "moduleCode",
            "moduleName",
            "lecturerStaffID",
            "lengthInWeeks",
            "startWeek",
            "sessions",
            "enrolledStudents"
        },
        module.getCSVHeader()
    );
  }
}
