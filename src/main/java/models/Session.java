package models;
import java.time.LocalTime;

/**
 * The type Session.
 */
/* Session.java
 *
 * Represents a session in the system.
 * Contains attributes such as module and lecturer.
 */
public abstract class Session {

    private Module module;
    private Room room;
    private int startWeek;
    private int endWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Lecturer lecturer;
    private Day day;

    /**
     * Instantiates a new Session.
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
    public Session(Module module, Lecturer lecturer, Room room, Day day, int startWeek, LocalTime startTime, LocalTime endTime, int endWeek) {
        this.module = module;
        this.lecturer = lecturer;
        this.room = room;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        validateTimes();
    }

    /**
     * Requires groups boolean.
     *
     * @return the boolean
     */
    public abstract boolean requiresGroups();

    private void validateTimes() {
        LocalTime earliest = LocalTime.of(8, 0);
        LocalTime latest = LocalTime.of(22, 0);

        if (endTime.isBefore(startTime) || endTime.equals(startTime)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }

        if (startTime.isBefore(earliest) || endTime.isAfter(latest)) {
            throw new IllegalArgumentException("Session must be from 8am to 10pm"); // 10pm lectures go HARD
        }
    }

    /**
     * Gets room.
     *
     * @return the room
     */
    public Room getRoom() {
        return this.room;
    }

    /**
     * Sets room.
     *
     * @param room the room
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Gets lecturer.
     *
     * @return the lecturer
     */
    public Lecturer getLecturer() {
        return lecturer;
    }

    /**
     * Sets lecturer.
     *
     * @param lecturer the lecturer
     */
    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    /**
     * Gets start week.
     *
     * @return the start week
     */
    public int getStartWeek() {
        return startWeek;
    }

    /**
     * Sets start week.
     *
     * @param startWeek the start week
     */
    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    /**
     * Gets end week.
     *
     * @return the end week
     */
    public int getEndWeek() {
        return endWeek;
    }

    /**
     * Sets end week.
     *
     * @param endWeek the end week
     */
    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets start time.
     *
     * @param startTime the start time
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Sets end time.
     *
     * @param endTime the end time
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets module.
     *
     * @return the module
     */
    public Module getModule() {
        return this.module;
    }

    /**
     * Sets module.
     *
     * @param module the module
     */
    public void setModule(Module module) {
        this.module = module;
    }

    /**
     * Gets day.
     *
     * @return the day
     */
    public Day getDay() {
        return day;
    }

    /**
     * Sets day.
     *
     * @param day the day
     */
    public void setDay(Day day) {
        this.day = day;
    }

    /**
     * The enum Day.
     */
    public enum Day {
        /**
         * Monday day.
         */
        MONDAY,
        /**
         * Tuesday day.
         */
        TUESDAY,
        /**
         * Wednesday day.
         */
        WEDNESDAY,
        /**
         * Thursday day.
         */
        THURSDAY,
        /**
         * Friday day.
         */
        FRIDAY,
        /**
         * Saturday day.
         */
        SATURDAY,
        /**
         * Sunday day.
         */
        SUNDAY
    }
}
