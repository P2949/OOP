/*Group.java
 *
 * Represents a group of students associated with a specific session type (Laboratory, Tutorial, or Lecture).
 */
package models;
import java.util.ArrayList;
import java.util.List;

public class Group {

    private char groupID;
    private final Session session;
    private List<Student> students = new ArrayList<>();

    public Group(char groupID, Session session) {
        this.groupID = groupID;
        this.session = session;
        session.getModule().addSession(this);
        session.getRoom().addSession(this);
    }

    public Group(char groupID, Session session, List<Student> students) {
        this.groupID = groupID;
        this.session = session;
        this.students = new ArrayList<>(students);
        session.getModule().addSession(this);
        session.getRoom().addSession(this);
    }

    public Session getSession() {
        return session;
    }

    public char getGroupID() {
        return groupID;
    }

    public void setGroupID(char groupID) {
        this.groupID = groupID;
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public void setStudents(List<Student> students) {
        this.students = new ArrayList<>(students);
        List<Student> tempStudents = new ArrayList<>(this.students);
        session.getModule().setEnrolledStudents(tempStudents);
    }

    public void addStudent(Student student) {
        if (!this.students.contains(student)) {
            this.students.add(student);
            if (!student.getGroups().contains(this)) {
                student.addGroup(this);
            }
        }
    }

    public void removeStudent(Student student) {
        if (this.students.remove(student)) {
            if (student.getGroups().contains(this)) {
                student.removeGroup(this);
            }
        }
    }

}
