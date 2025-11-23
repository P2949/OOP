package models;

import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;

/* Lecture.java
 *
 * Represents a Lecture session, which is a specific type of Session.
 */
public class Lecture extends Session implements CSVModel {

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

    @Override
    public String[] toCSVRow() {
        return new String[]{
            "Lecture",
            getModule().getModuleCode(),
            getLecturer().getStaffID(),
            getRoom().getRoomNumber(),
            getDay().name(),
            String.valueOf(getStartWeek()),
            getStartTime().toString(),
            getEndTime().toString(),
            String.valueOf(getEndWeek())
        };
    }

    @Override
    public String[] getCSVHeader() {
        return new String[]{"type", "moduleCode", "lecturerStaffID", "roomNumber", "day", "startWeek", "startTime", "endTime", "endWeek"};
    }
}
