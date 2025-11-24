/*Group.java
 *
 * Represents a group of students associated with a specific session type (Laboratory, Tutorial, or Lecture).
 */
package models;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Group.
 */
public class Group implements CSVModel {

    private char groupID;
    private final Session session;
    private List<Student> students = new ArrayList<>();

    /**
     * Instantiates a new Group.
     *
     * @param groupID the group id
     * @param session the session
     */
    public Group(char groupID, @NotNull Session session) {
        this.groupID = groupID;
        this.session = session;
        session.getModule().addSession(this);
        session.getRoom().addSession(this);
    }

    /**
     * Instantiates a new Group.
     *
     * @param groupID  the group id
     * @param session  the session
     * @param students the students
     */
    public Group(char groupID, @NotNull Session session, List<Student> students) {
        this.groupID = groupID;
        this.session = session;
        this.students = new ArrayList<>(students);
        session.getModule().addSession(this);
        session.getRoom().addSession(this);
    }

    /**
     * Gets session.
     *
     * @return the session
     */
    public Session getSession() {
        return session;
    }

    /**
     * Gets group id.
     *
     * @return the group id
     */
    public char getGroupID() {
        return groupID;
    }

    /**
     * Sets group id.
     *
     * @param groupID the group id
     */
    public void setGroupID(char groupID) {
        this.groupID = groupID;
    }

    /**
     * Gets students.
     *
     * @return the students
     */
    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    /**
     * Sets students.
     *
     * @param students the students
     */
    public void setStudents(List<Student> students) {
        this.students = new ArrayList<>(students);
        List<Student> tempStudents = new ArrayList<>(this.students);
        session.getModule().setEnrolledStudents(tempStudents);
    }

    /**
     * Add student.
     *
     * @param student the student
     */
    public void addStudent(Student student) {
        if (!this.students.contains(student)) {
            this.students.add(student);
            if (!student.getGroups().contains(this)) {
                student.addGroup(this);
            }
        }
    }

    /**
     * Remove student.
     *
     * @param student the student
     */
    public void removeStudent(Student student) {
        if (this.students.remove(student)) {
            if (student.getGroups().contains(this)) {
                student.removeGroup(this);
            }
        }
    }

    @Override
    public String[] toCSVRow() {
        String studentIDs = students.stream()
            .map(s -> String.valueOf(s.getStudentID()))
            .collect(java.util.stream.Collectors.joining(","));
        String sessionKey = session.getClass().getSimpleName() + ":" +
                           session.getModule().getModuleCode() + ":" +
                           session.getDay() + ":" +
                           session.getStartTime() + ":" +
                           session.getRoom().getRoomNumber();
        return new String[]{
            String.valueOf(groupID),
            sessionKey,
            studentIDs
        };
    }

    @Override
    public String[] getCSVHeader() {
        return new String[]{"groupID", "session", "students"};
    }
}
