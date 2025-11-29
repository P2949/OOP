package controllers;

import models.*;
import models.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import views.StudentView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentControllerTest {

  private Student student;
  private StudentView view;
  private StudentController controller;

  @BeforeEach
  void setup() {
    student = mock(Student.class);
    view = mock(StudentView.class);
    controller = new StudentController(student, view);
  }

  @Test
  void getModules_returnsEmptyList_whenProgramIsNull() {
    when(student.getProgram()).thenReturn(null);

    List<Module> modules = controller.getModules();

    assertNotNull(modules);
    assertTrue(modules.isEmpty());
  }

  @Test
  void getModules_returnsCopyOfProgramModules() {
    Module m1 = mock(Module.class);
    Module m2 = mock(Module.class);

    Program program = mock(Program.class);
    when(program.getModulesTaught()).thenReturn(List.of(m1, m2));
    when(student.getProgram()).thenReturn(program);

    List<Module> result = controller.getModules();

    assertEquals(2, result.size());
    assertTrue(result.contains(m1));
    assertTrue(result.contains(m2));
    assertNotSame(program.getModulesTaught(), result);
  }

  @Test
  void getLaboratories_returnsOnlyLaboratorySessions() {
    Laboratory lab = mock(Laboratory.class);
    Lecture lec = mock(Lecture.class);
    Tutorial tut = mock(Tutorial.class);

    Group g1 = mock(Group.class);
    Group g2 = mock(Group.class);
    Group g3 = mock(Group.class);

    when(g1.getSession()).thenReturn(lab);
    when(g2.getSession()).thenReturn(lec);
    when(g3.getSession()).thenReturn(tut);

    when(student.getGroups()).thenReturn(List.of(g1, g2, g3));

    List<Laboratory> result = controller.getLaboratories();

    assertEquals(1, result.size());
    assertSame(lab, result.get(0));
  }

  @Test
  void getTutorials_returnsOnlyTutorialSessions() {
    Tutorial tut1 = mock(Tutorial.class);
    Laboratory lab = mock(Laboratory.class);

    Group g1 = mock(Group.class);
    Group g2 = mock(Group.class);

    when(g1.getSession()).thenReturn(tut1);
    when(g2.getSession()).thenReturn(lab);

    when(student.getGroups()).thenReturn(List.of(g1, g2));

    List<Tutorial> result = controller.getTutorials();

    assertEquals(1, result.size());
    assertSame(tut1, result.get(0));
  }

  @Test
  void getLectures_returnsOnlyLectureSessions() {
    Lecture lecture1 = mock(Lecture.class);
    Lecture lecture2 = mock(Lecture.class);
    Laboratory lab = mock(Laboratory.class);

    Group g3 = mock(Group.class);
    Group g4 = mock(Group.class);
    Group g5 = mock(Group.class);

    when(g3.getSession()).thenReturn(lecture1);
    when(g4.getSession()).thenReturn(lecture2);
    when(g5.getSession()).thenReturn(lab);

    Module mod1 = mock(Module.class);
    when(mod1.getSessions()).thenReturn(List.of(g3, g4, g5));

    Program program = mock(Program.class);
    when(program.getModulesTaught()).thenReturn(List.of(mod1));

    when(student.getProgram()).thenReturn(program);

    List<Lecture> result = controller.getLectures();

    assertEquals(2, result.size());
    assertTrue(result.contains(lecture1));
    assertTrue(result.contains(lecture2));
  }

  @Test
  void getLectures_emptyListWhenProgramIsNull() {
    when(student.getProgram()).thenReturn(null);

    assertTrue(controller.getLectures().isEmpty());
  }

  @Test
  void getCurrentStudent_returnsInjectedStudent() {
    assertSame(student, controller.getCurrentStudent());
  }
}
