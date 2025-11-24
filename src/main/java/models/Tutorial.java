package models;
import java.time.LocalTime;

/**
 * The type Tutorial.
 */
/* Tutorial.java
 *
 * Represents a Tutorial session, which is a specific type of Session.
 */
public class Tutorial extends Session implements CSVModel {

    /**
     * Instantiates a new Tutorial.
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
    public Tutorial(Module module, Lecturer lecturer, Room room, Session.Day day, int startWeek, LocalTime startTime, LocalTime endTime, int endWeek) {
        super(module, lecturer, room, day, startWeek, startTime, endTime, endWeek);
    }

    @Override
    public boolean requiresGroups() {
        return true;
    }

    @Override
    public String[] toCSVRow() {
        return new String[]{
            "Tutorial",
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

