package views;

import models.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class StudentViewTest {

  @Test
  void interfaceHasExpectedMethods() {
    Method[] methods = StudentView.class.getDeclaredMethods();

    assertEquals(6, methods.length);

    assertDoesNotThrow(() -> StudentView.class.getMethod("displayTimeTable", Student.class));
    assertDoesNotThrow(() -> StudentView.class.getMethod("displayModules", java.util.List.class));
    assertDoesNotThrow(() -> StudentView.class.getMethod("displayLaboratories", java.util.List.class));
    assertDoesNotThrow(() -> StudentView.class.getMethod("displayTutorials", java.util.List.class));
    assertDoesNotThrow(() -> StudentView.class.getMethod("displayLectures", java.util.List.class));
    assertDoesNotThrow(() -> StudentView.class.getMethod("showMessage", String.class));
  }

  @Test
  void studentViewIsInterface() {
    assertTrue(StudentView.class.isInterface());
  }
}
