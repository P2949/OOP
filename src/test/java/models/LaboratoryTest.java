package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LaboratoryTest {

  private Module module;
  private Lecturer lecturer;
  private Room room;

  @BeforeEach
  void setup() {
    module = mock(Module.class);
    lecturer = mock(Lecturer.class);
    room = mock(Room.class);

    when(module.getModuleCode()).thenReturn("CS101");
    when(lecturer.getStaffID()).thenReturn("244");
    when(room.getType()).thenReturn(Room.RoomType.LABORATORY);
    when(room.getRoomNumber()).thenReturn("CS123");
  }

  @Test
  void constructor_acceptsValidLaboratoryRoom() {
    Laboratory lab = new Laboratory(
        module,
        lecturer,
        room,
        Session.Day.MONDAY,
        1,
        LocalTime.of(9, 0),
        LocalTime.of(11, 0),
        12
    );

    assertEquals(module, lab.getModule());
    assertEquals(lecturer, lab.getLecturer());
    assertEquals(room, lab.getRoom());
    assertEquals(Session.Day.MONDAY, lab.getDay());
    assertEquals(1, lab.getStartWeek());
    assertEquals(12, lab.getEndWeek());
    assertEquals(LocalTime.of(9, 0), lab.getStartTime());
    assertEquals(LocalTime.of(11, 0), lab.getEndTime());
  }

  @Test
  void constructor_throwsExceptionIfRoomNotLaboratory() {
    Room wrongRoom = mock(Room.class);
    when(wrongRoom.getType()).thenReturn(Room.RoomType.CLASSROOM);

    assertThrows(IllegalArgumentException.class, () ->
        new Laboratory(
            module,
            lecturer,
            wrongRoom,
            Session.Day.MONDAY,
            1,
            LocalTime.of(9, 0),
            LocalTime.of(11, 0),
            12
        )
    );
  }

  @Test
  void requiresGroups_returnsTrue() {
    Laboratory lab = new Laboratory(
        module,
        lecturer,
        room,
        Session.Day.TUESDAY,
        1,
        LocalTime.of(10, 0),
        LocalTime.of(12, 0),
        5
    );

    assertTrue(lab.requiresGroups());
  }

  @Test
  void toCSVRow_returnsExpectedValues() {
    Laboratory lab = new Laboratory(
        module,
        lecturer,
        room,
        Session.Day.WEDNESDAY,
        2,
        LocalTime.of(13, 0),
        LocalTime.of(15, 0),
        8
    );

    String[] csv = lab.toCSVRow();

    assertArrayEquals(
        new String[]{
            "Laboratory",
            "CS101",
            "244",
            "CS123",
            "WEDNESDAY",
            "2",
            "13:00",
            "15:00",
            "8"
        },
        csv
    );
  }

  @Test
  void getCSVHeader_returnsExpectedHeader() {
    Laboratory lab = new Laboratory(
        module,
        lecturer,
        room,
        Session.Day.MONDAY,
        1,
        LocalTime.of(9, 0),
        LocalTime.of(10, 0),
        5
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
        lab.getCSVHeader()
    );
  }
}
