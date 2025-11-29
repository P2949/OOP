package controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimetableControllerTest {

  @Test
  void timetableController_canBeConstructed() {
    TimetableController controller = new TimetableController();
    assertNotNull(controller);
  }

  @Test
  void timetableController_hasNoPublicMethods() {
    var methods = TimetableController.class.getDeclaredMethods();
    assertEquals(0, methods.length, "TimetableController should not define methods yet");
  }
}
