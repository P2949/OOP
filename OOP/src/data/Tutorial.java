package data;
import java.time.LocalTime;

/* Tutorial.java
 *
 * Represents a Tutorial session, which is a specific type of Session.
 */
public class Tutorial extends Session {

    public Tutorial(Module module, Lecturer lecturer, Room room, Session.Day day, int startWeek, LocalTime startTime, LocalTime endTime, int endWeek) {
        super(module, lecturer, room, day, startWeek, startTime, endTime, endWeek);
    }

    @Override
    public boolean requiresGroups() {
        return true;
    }

}

