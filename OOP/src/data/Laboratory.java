package data;
import java.time.LocalTime;

/*
 * Lab.java
 *
 * Represents a Laboratory session, which is a specific type of Session.
 */
public class Laboratory extends Session {

    public Laboratory(Module module, Lecturer lecturer, Room room, Session.Day day, int startWeek, LocalTime startTime, LocalTime endTime, int endWeek) {
        super(module, lecturer, requireLabRoom(room), day, startWeek, startTime, endTime, endWeek);
    }

    private static Room requireLabRoom(Room room) {
        if (!Room.RoomType.LABORATORY.name().matches((room.getType().name()))) {
            throw new IllegalArgumentException("Room type must be LABORATORY for Lab sessions.");
        }
        return room;
    }

    @Override
    public boolean requiresGroups() {
        return true;
    }
}
