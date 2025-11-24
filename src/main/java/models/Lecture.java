package models;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;

/**
 * The type Lecture.
 */
/* Lecture.java
 *
 * Represents a Lecture session, which is a specific type of Session.
 */
public class Lecture extends Session implements CSVModel {

    /**
     * Instantiates a new Lecture.
     *
     * @param module    the module
     * @param lecturer  the lecturer
     * @param room      the room
     * @param day       the day
     * @param startWeek the start week
     * @param startTime the start time
     * @param endTime   the end time
     * @param endWeek   the end week
     */
    public Lecture(Module module, Lecturer lecturer, Room room, Session.Day day, int startWeek, LocalTime startTime, LocalTime endTime, int endWeek) {
        super(module, lecturer, requireLectureRoom(room), day, startWeek, startTime, endTime, endWeek);
    }

    /**
     * Require lecture room.
     *
     * @param room the room
     * @return the room
     */
    @Contract("_ -> param1")
    public static @NotNull Room requireLectureRoom(@NotNull Room room) {
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
