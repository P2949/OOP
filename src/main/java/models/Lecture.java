package models;

import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;

/* Lecture.java
 *
 * Represents a Lecture session, which is a specific type of Session.
 */
public class Lecture extends Session {

    public Lecture(Module module, Lecturer lecturer, Room room, Session.Day day, int startWeek, LocalTime startTime, LocalTime endTime, int endWeek) {
        super(module, lecturer, requireLectureRoom(room), day, startWeek, startTime, endTime, endWeek);
    }

    public static Room requireLectureRoom(@NotNull Room room) {
        if (!Room.RoomType.CLASSROOM.name().matches((room.getType().name()))) {
            throw new IllegalArgumentException("Room type must be CLASSROOM for Lecture sessions.");
        }
        return room;
    }

    @Override
    public boolean requiresGroups() {
        return false;
    }

}
