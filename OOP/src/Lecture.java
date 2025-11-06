import java.time.LocalTime;

/* Lecture.java
 *
 * Represents a Lecture session, which is a specific type of Session.
 */
public class Lecture extends Session {

    public Lecture(Module module, Lecturer lecturer, Room room, Session.Day day, int startWeek, LocalTime startTime, LocalTime endTime, int endWeek) {
        super(module, lecturer, room, day, startWeek, startTime, endTime, endWeek);
    }

    @Override
    public boolean requiresGroups() {
        return false;
    }

}

