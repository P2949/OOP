package models;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessionTest {

  static class TestSession extends Session {
    public TestSession(Module module, Lecturer lecturer, Room room, Day day,
        int startWeek, LocalTime startTime, LocalTime endTime, int endWeek) {
      super(module, lecturer, room, day, startWeek, startTime, endTime, endWeek);
    }

    @Override
    public boolean requiresGroups() {
      return true;
    }
  }

  @Test
  void constructor_acceptsValidTimes() {
    Module m = mock(Module.class);
    Lecturer l = mock(Lecturer.class);
    Room r = mock(Room.class);

    Session s = new TestSession(
        m, l, r, Session.Day.MONDAY,
        1,
        LocalTime.of(9, 0),
        LocalTime.of(11, 0),
        12
    );

    assertEquals(m, s.getModule());
    assertEquals(l, s.getLecturer());
    assertEquals(r, s.getRoom());
    assertEquals(1, s.getStartWeek());
    assertEquals(12, s.getEndWeek());
    assertEquals(LocalTime.of(9, 0), s.getStartTime());
    assertEquals(LocalTime.of(11, 0), s.getEndTime());
  }

  @Test
  void constructor_throwsIfEndTimeEqualsStartTime() {
    Module m = mock(Module.class);
    Lecturer l = mock(Lecturer.class);
    Room r = mock(Room.class);

    assertThrows(IllegalArgumentException.class, () ->
        new TestSession(
            m, l, r, Session.Day.MONDAY,
            1,
            LocalTime.of(10, 0),
            LocalTime.of(10, 0),
            12
        )
    );
  }

  @Test
  void constructor_throwsIfEndTimeBeforeStartTime() {
    Module m = mock(Module.class);
    Lecturer l = mock(Lecturer.class);
    Room r = mock(Room.class);

    assertThrows(IllegalArgumentException.class, () ->
        new TestSession(
            m, l, r, Session.Day.MONDAY,
            1,
            LocalTime.of(12, 0),
            LocalTime.of(11, 0),
            12
        )
    );
  }

  @Test
  void constructor_throwsIfStartBefore8am() {
    Module m = mock(Module.class);
    Lecturer l = mock(Lecturer.class);
    Room r = mock(Room.class);

    assertThrows(IllegalArgumentException.class, () ->
        new TestSession(
            m, l, r, Session.Day.MONDAY,
            1,
            LocalTime.of(7, 59),
            LocalTime.of(10, 0),
            12
        )
    );
  }

  @Test
  void constructor_throwsIfEndAfter10pm() {
    Module m = mock(Module.class);
    Lecturer l = mock(Lecturer.class);
    Room r = mock(Room.class);

    assertThrows(IllegalArgumentException.class, () ->
        new TestSession(
            m, l, r, Session.Day.MONDAY,
            1,
            LocalTime.of(20, 0),
            LocalTime.of(22, 1),
            12
        )
    );
  }

  @Test
  void gettersAndSettersWorkCorrectly() {
    Module m1 = mock(Module.class);
    Lecturer l1 = mock(Lecturer.class);
    Room r1 = mock(Room.class);

    TestSession s = new TestSession(
        m1, l1, r1, Session.Day.WEDNESDAY,
        2,
        LocalTime.of(10, 0),
        LocalTime.of(12, 0),
        12
    );

    Module m2 = mock(Module.class);
    Lecturer l2 = mock(Lecturer.class);
    Room r2 = mock(Room.class);

    s.setModule(m2);
    s.setLecturer(l2);
    s.setRoom(r2);

    assertEquals(m2, s.getModule());
    assertEquals(l2, s.getLecturer());
    assertEquals(r2, s.getRoom());

    s.setStartWeek(3);
    s.setEndWeek(9);
    assertEquals(3, s.getStartWeek());
    assertEquals(9, s.getEndWeek());

    s.setStartTime(LocalTime.of(8, 0));
    s.setEndTime(LocalTime.of(20, 0));
    assertEquals(LocalTime.of(8, 0), s.getStartTime());
    assertEquals(LocalTime.of(20, 0), s.getEndTime());

    s.setDay(Session.Day.SUNDAY);
    assertEquals(Session.Day.SUNDAY, s.getDay());
  }

  @Test
  void requiresGroups_isImplementedBySubclass() {
    Session s = new TestSession(
        mock(Module.class),
        mock(Lecturer.class),
        mock(Room.class),
        Session.Day.FRIDAY,
        1,
        LocalTime.of(9, 0),
        LocalTime.of(11, 0),
        12
    );

    assertTrue(s.requiresGroups());
  }
}
