package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroupTest {

  private Module module;
  private Room room;
  private Session session;

  @BeforeEach
  void setup() {
    module = mock(Module.class);
    room = mock(Room.class);

    session = mock(Session.class);
    when(session.getModule()).thenReturn(module);
    when(session.getRoom()).thenReturn(room);

    when(module.getModuleCode()).thenReturn("CS101");
    when(session.getDay()).thenReturn(Session.Day.MONDAY);
    when(session.getStartTime()).thenReturn(LocalTime.of(9, 0));
    when(room.getRoomNumber()).thenReturn("R1");
  }

  @Test
  void constructor_addsGroupToModuleAndRoom() {
    Group g = new Group('A', session);

    verify(module).addSession(g);
    verify(room).addSession(g);
  }

  @Test
  void constructor_withStudents_initializesStudentList() {
    Student s1 = mock(Student.class);
    Student s2 = mock(Student.class);

    Group g = new Group('B', session, List.of(s1, s2));

    List<Student> result = g.getStudents();
    assertEquals(2, result.size());
    assertTrue(result.contains(s1));
    assertTrue(result.contains(s2));

    verify(module).addSession(g);
    verify(room).addSession(g);
  }

  @Test
  void getGroupID_and_setGroupID_workCorrectly() {
    Group g = new Group('A', session);
    assertEquals('A', g.getGroupID());

    g.setGroupID('Z');
    assertEquals('Z', g.getGroupID());
  }

  @Test
  void getStudents_returnsDefensiveCopy() {
    Group g = new Group('A', session);

    Student s = mock(Student.class);
    g.setStudents(List.of(s));

    List<Student> list1 = g.getStudents();
    List<Student> list2 = g.getStudents();

    assertNotSame(list1, list2);
    assertEquals(1, list1.size());
  }

  @Test
  void setStudents_updatesModuleEnrolledStudents() {
    Group g = new Group('A', session);

    Student s1 = mock(Student.class);
    Student s2 = mock(Student.class);

    g.setStudents(List.of(s1, s2));

    verify(module).setEnrolledStudents(anyList());
  }

  @Test
  void addStudent_addsIfNotPresent_andSyncsStudentBackReference() {
    Group g = new Group('A', session);

    Student s = mock(Student.class);
    when(s.getGroups()).thenReturn(List.of());

    g.addStudent(s);

    assertTrue(g.getStudents().contains(s));
    verify(s).addGroup(g);
  }

  @Test
  void addStudent_doesNothingIfStudentAlreadyInGroup() {
    Group g = new Group('A', session);

    Student s = mock(Student.class);
    when(s.getGroups()).thenReturn(List.of(g));

    g.setStudents(List.of(s));
    g.addStudent(s);

    verify(s, never()).addGroup(g);
  }

  @Test
  void removeStudent_removesAndSyncsStudentBackReference() {
    Group g = new Group('A', session);

    Student s = mock(Student.class);
    when(s.getGroups()).thenReturn(List.of(g));

    g.setStudents(List.of(s));
    g.removeStudent(s);

    assertFalse(g.getStudents().contains(s));
    verify(s).removeGroup(g);
  }

  @Test
  void removeStudent_doesNothingIfNotPresent() {
    Group g = new Group('A', session);

    Student s = mock(Student.class);
    when(s.getGroups()).thenReturn(List.of());

    g.removeStudent(s);

    verify(s, never()).removeGroup(g);
  }

  @Test
  void toCSVRow_returnsFormattedRow() {
    Student s1 = mock(Student.class);
    Student s2 = mock(Student.class);
    when(s1.getStudentID()).thenReturn(10);
    when(s2.getStudentID()).thenReturn(20);

    Group g = new Group('A', session, List.of(s1, s2));

    String[] csv = g.toCSVRow();

    String expectedSessionKey =
        session.getClass().getSimpleName() +
            ":CS101:MONDAY:09:00:R1";

    assertEquals("A", csv[0]);
    assertEquals(expectedSessionKey, csv[1]);
    assertEquals("10,20", csv[2]);
  }

  @Test
  void getCSVHeader_returnsCorrectHeader() {
    Group g = new Group('A', session);
    assertArrayEquals(new String[]{"groupID", "session", "students"}, g.getCSVHeader());
  }
}
