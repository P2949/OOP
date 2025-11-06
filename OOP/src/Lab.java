import java.time.LocalTime;

/*
 * Lab.java
 *
 * Represents a Lab session, which is a specific type of Session.
 */
public class Lab extends Session {

    public Lab(Module module, Lecturer lecturer, Room room, Session.Day day, int startWeek, LocalTime startTime, LocalTime endTime, int endWeek) {
        super(module, lecturer, room, day, startWeek, startTime, endTime, endWeek);
    }

    @Override
    public boolean requiresGroups() {
        return true;
    }
}
