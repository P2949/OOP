
import java.time.LocalTime;

public class test {

    public static void main(String[] args) {
        Room A = new Room("101", 10, Room.RoomType.CLASSROOM, "Building A");
        Lecturer L = new Lecturer("Dr. Smith", 10, "L001", "Computer Science");
        Module M = new Module("CS101", "Introduction to Computer Science", L, 20, 1);
        Lecture a = new Lecture(M, L, A, Session.Day.MONDAY, 1, LocalTime.of(9, 0), LocalTime.of(11, 0), 20);
    }
}
