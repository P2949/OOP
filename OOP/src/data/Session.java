package data;
import java.time.LocalTime;

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

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Module getModule() {
        return this.module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}
