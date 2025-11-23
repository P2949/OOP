package models;

import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;

/*
 * Lab.java
 *
 * Represents a Laboratory session, which is a specific type of Session.
 */
public class Laboratory extends Session implements CSVModel {

    public Laboratory(Module module, Lecturer lecturer, Room room, Session.Day day, int startWeek, LocalTime startTime, LocalTime endTime, int endWeek) {
        super(module, lecturer, requireLabRoom(room), day, startWeek, startTime, endTime, endWeek);
    }

    private static Room requireLabRoom(@NotNull Room room) {
        if (!Room.RoomType.LABORATORY.name().matches((room.getType().name()))) {
            throw new IllegalArgumentException("Room type must be LABORATORY for Lab sessions.");
        }
        return room;
    }

    @Override
    public boolean requiresGroups() {
        return true;
    }

    @Override
    public String[] toCSVRow() {
        return new String[]{
            "Laboratory",
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
