package models;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TutorialTest {

  @Test
  void constructor_initializesFieldsCorrectly() {
    Module module = mock(Module.class);
    Lecturer lecturer = mock(Lecturer.class);
    Room room = mock(Room.class);

    Tutorial t = new Tutorial(
        module,
        lecturer,
        room,
        Session.Day.THURSDAY,
        1,
        LocalTime.of(10, 0),
        LocalTime.of(12, 0),
        12
    );

    assertEquals(module, t.getModule());
    assertEquals(lecturer, t.getLecturer());
    assertEquals(room, t.getRoom());
    assertEquals(Session.Day.THURSDAY, t.getDay());
    assertEquals(1, t.getStartWeek());
    assertEquals(12, t.getEndWeek());
    assertEquals(LocalTime.of(10, 0), t.getStartTime());
    assertEquals(LocalTime.of(12, 0), t.getEndTime());
  }

  @Test
  void constructor_throwsForInvalidTimes() {
    Module module = mock(Module.class);
    Lecturer lecturer = mock(Lecturer.class);
    Room room = mock(Room.class);

    assertThrows(IllegalArgumentException.class, () ->
        new Tutorial(
            module, lecturer, room, Session.Day.MONDAY,
            1,
            LocalTime.of(10, 0),
            LocalTime.of(10, 0),
            12
        )
    );

    assertThrows(IllegalArgumentException.class, () ->
        new Tutorial(
            module, lecturer, room, Session.Day.MONDAY,
            1,
            LocalTime.of(11, 0),
            LocalTime.of(10, 0),
            12
        )
    );

    assertThrows(IllegalArgumentException.class, () ->
        new Tutorial(
            module, lecturer, room, Session.Day.MONDAY,
            1,
            LocalTime.of(7, 59),
            LocalTime.of(10, 0),
            12
        )
    );

    assertThrows(IllegalArgumentException.class, () ->
        new Tutorial(
            module, lecturer, room, Session.Day.MONDAY,
            1,
            LocalTime.of(20, 0),
            LocalTime.of(22, 1),
            12
        )
    );
  }

  @Test
  void requiresGroups_returnsTrue() {
    Tutorial t = new Tutorial(
        mock(Module.class),
        mock(Lecturer.class),
        mock(Room.class),
        Session.Day.FRIDAY,
        1,
        LocalTime.of(9, 0),
        LocalTime.of(11, 0),
        12
    );

    assertTrue(t.requiresGroups());
  }

  @Test
  void toCSVRow_outputsCorrectValues() {
    Module module = mock(Module.class);
    Lecturer lecturer = mock(Lecturer.class);
    Room room = mock(Room.class);

    when(module.getModuleCode()).thenReturn("CS123");
    when(lecturer.getStaffID()).thenReturn("SID123");
    when(room.getRoomNumber()).thenReturn("A123");

    Tutorial t = new Tutorial(
        module,
        lecturer,
        room,
        Session.Day.WEDNESDAY,
        1,
        LocalTime.of(14, 0),
        LocalTime.of(16, 0),
        12
    );

    String[] csv = t.toCSVRow();

    assertArrayEquals(
        new String[]{
            "Tutorial",
            "CS123",
            "SID123",
            "A123",
            "WEDNESDAY",
            "1",
            "14:00",
            "16:00",
            "12"
        },
        csv
    );
  }

  @Test
  void getCSVHeader_isCorrect() {
    Tutorial t = new Tutorial(
        mock(Module.class),
        mock(Lecturer.class),
        mock(Room.class),
        Session.Day.MONDAY,
        1,
        LocalTime.of(9, 0),
        LocalTime.of(10, 0),
        12
    );

    assertArrayEquals(
        new String[]{
            "type",
            "moduleCode",
            "lecturerStaffID",
            "roomNumber",
            "day",
            "startWeek",
            "startTime",
            "endTime",
            "endWeek"
        },
        t.getCSVHeader()
    );
  }
}
