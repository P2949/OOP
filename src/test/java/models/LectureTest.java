package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LectureTest {

  private Module module;
  private Lecturer lecturer;
  private Room room;

  @BeforeEach
  void setup() {
    module = mock(Module.class);
    lecturer = mock(Lecturer.class);
    room = mock(Room.class);

    when(module.getModuleCode()).thenReturn("CS123");
    when(lecturer.getStaffID()).thenReturn("123");
    when(room.getType()).thenReturn(Room.RoomType.CLASSROOM);
    when(room.getRoomNumber()).thenReturn("A103");
  }

  @Test
  void constructor_acceptsValidClassroomRoom() {
    Lecture lecture = new Lecture(
        module,
        lecturer,
        room,
        Session.Day.MONDAY,
        1,
        LocalTime.of(9, 0),
        LocalTime.of(11, 0),
        12
    );

    assertEquals(module, lecture.getModule());
    assertEquals(lecturer, lecture.getLecturer());
    assertEquals(room, lecture.getRoom());
    assertEquals(Session.Day.MONDAY, lecture.getDay());
    assertEquals(1, lecture.getStartWeek());
    assertEquals(12, lecture.getEndWeek());
    assertEquals(LocalTime.of(9, 0), lecture.getStartTime());
    assertEquals(LocalTime.of(11, 0), lecture.getEndTime());
  }

  @Test
  void constructor_throwsIfRoomNotClassroom() {
    Room wrongRoom = mock(Room.class);
    when(wrongRoom.getType()).thenReturn(Room.RoomType.LABORATORY);

    assertThrows(IllegalArgumentException.class, () ->
        new Lecture(
            module,
            lecturer,
            wrongRoom,
            Session.Day.TUESDAY,
            1,
            LocalTime.of(10, 0),
            LocalTime.of(12, 0),
            5
        )
    );
  }

  @Test
  void requiresGroups_returnsFalse() {
    Lecture lecture = new Lecture(
        module,
        lecturer,
        room,
        Session.Day.WEDNESDAY,
        1,
        LocalTime.of(8, 0),
        LocalTime.of(10, 0),
        6
    );

    assertFalse(lecture.requiresGroups());
  }

  @Test
  void toCSVRow_producesExpectedOutput() {
    Lecture lecture = new Lecture(
        module,
        lecturer,
        room,
        Session.Day.FRIDAY,
        3,
        LocalTime.of(14, 0),
        LocalTime.of(16, 0),
        10
    );

    String[] csv = lecture.toCSVRow();

    assertArrayEquals(
        new String[]{
            "Lecture",
            "CS123",
            "123",
            "A103",
            "FRIDAY",
            "3",
            "14:00",
            "16:00",
            "10"
        },
        csv
    );
  }

  @Test
  void getCSVHeader_returnsCorrectHeader() {
    Lecture lecture = new Lecture(
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
        lecture.getCSVHeader()
    );
  }
}
