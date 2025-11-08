import java.time.LocalTime;

public class test {

    public static void main(String[] args) {
        data.Room A = new data.Room("101", 10, data.Room.RoomType.CLASSROOM, "Building A");
        data.Lecturer L = new data.Lecturer("Dr. Smith", 10, "L001", "Computer Science");
        data.Module M = new data.Module("CS101", "Introduction to Computer Science", L, 20, 1);
        data.Lecture a = new data.Lecture(M, L, A, data.Session.Day.MONDAY, 1, LocalTime.of(9, 0), LocalTime.of(11, 0), 20);
        user.View u = new user.View();
        u.displayMessage("aaaa");
    }
}
