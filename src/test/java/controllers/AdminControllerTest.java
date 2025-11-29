package controllers;

import models.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import views.AdminView;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminControllerTest {

  private AdminView view;
  private AdminController controller;

  @BeforeEach
  void setUp() {
    view = mock(AdminView.class);
    controller = new AdminController(view);
  }

  @Test
  void createStudent_addsStudentAndShowsSuccessMessage() {
    Student mockStudent = mock(Student.class);
    when(view.addStudent()).thenReturn(mockStudent);

    controller.createStudent();

    List<Student> students = controller.getAllStudents();
    assertTrue(students.contains(mockStudent));
    verify(view).showMessage("Student created successfully");
  }

  @Test
  void getAllStudents_returnsDefensiveCopy() {
    Student mockStudent = mock(Student.class);
    when(view.addStudent()).thenReturn(mockStudent);
    controller.createStudent();

    List<Student> first = controller.getAllStudents();
    List<Student> second = controller.getAllStudents();

    assertNotSame(first, second);
    assertEquals(first.size(), second.size());
  }

  @Test
  void start_whenExitImmediately_showsExitingMessage() {
    when(view.showMenu()).thenReturn(0);

    controller.start();

    verify(view).showMessage("Exiting...");
  }

  @Test
  void enrollStudentInCourse_withNoStudents_showsNoStudentsMessage() throws Exception {
    Field studentsField = AdminController.class.getDeclaredField("students");
    studentsField.setAccessible(true);
    @SuppressWarnings("unchecked")
    List<Student> students = (List<Student>) studentsField.get(controller);
    students.clear();

    var method = AdminController.class.getDeclaredMethod("enrollStudentInCourse");
    method.setAccessible(true);
    method.invoke(controller);

    verify(view).showMessage("No students available");
  }
}
