package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomTest {

  private Room room;

  @BeforeEach
  void setup() {
    room = new Room("A123", 100, Room.RoomType.CLASSROOM, "Main");
  }

  @Test
  void constructor_initializesFieldsCorrectly() {
    assertEquals("A123", room.getRoomNumber());
    assertEquals(100, room.getCapacity());
    assertEquals(Room.RoomType.CLASSROOM, room.getType());
    assertEquals("Main", room.getBuilding());
    assertFalse(room.isOccupied());
  }

  @Test
  void setRoomNumber_updatesCorrectly() {
    room.SetRoomNumber("B456");
    assertEquals("B456", room.getRoomNumber());
  }

  @Test
  void setCapacity_updatesCorrectly() {
    room.SetCapacity(90);
    assertEquals(90, room.getCapacity());
  }

  @Test
  void setOccupied_updatesStateCorrectly() {
    room.setOccupied(true);
    assertTrue(room.isOccupied());

    room.setOccupied(false);
    assertFalse(room.isOccupied());
  }

  @Test
  void setType_updatesRoomType() {
    room.setType(Room.RoomType.LABORATORY);
    assertEquals(Room.RoomType.LABORATORY, room.getType());
  }

  @Test
  void setBuilding_updatesCorrectly() {
    room.setBuilding("Foundation");
    assertEquals("Foundation", room.getBuilding());
  }

  @Test
  void getSessions_returnsDefensiveCopy() {
    Group g = mock(Group.class);
    room.addSession(g);

    List<Group> list1 = room.getSessions();
    List<Group> list2 = room.getSessions();

    assertEquals(1, list1.size());
    assertNotSame(list1, list2);
  }

  @Test
  void addSession_addsGroup() {
    Group g = mock(Group.class);

    room.addSession(g);

    assertTrue(room.getSessions().contains(g));
  }

  @Test
  void removeSession_removesGroup() {
    Group g = mock(Group.class);

    room.addSession(g);
    assertTrue(room.getSessions().contains(g));

    room.removeSession(g);
    assertFalse(room.getSessions().contains(g));
  }

  @Test
  void toCSVRow_outputsCorrectFormat() {
    room.setOccupied(true);

    String[] csv = room.toCSVRow();

    assertArrayEquals(
        new String[]{
            "A123",
            "100",
            "CLASSROOM",
            "Main",
            "true"
        },
        csv
    );
  }

  @Test
  void getCSVHeader_isCorrect() {
    assertArrayEquals(
        new String[]{"roomNumber", "capacity", "type", "building", "isOccupied"},
        room.getCSVHeader()
    );
  }
}
