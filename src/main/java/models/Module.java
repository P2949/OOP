
/* Module.java
 *
 * Represents a module in the system.
 * Contains attributes such as length in weeks, start week, module code, module name, lecturer, and enrolled students.
 */
package models;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * The type Module.
 */
public class Module implements CSVModel {

    private int lengthInWeeks;
    private int startWeek;
    private String moduleCode;
    private String moduleName;
    private Lecturer lecturer;
    private List<Group> sessions = new LinkedList<>();
    private List<Student> enrolledStudents = new LinkedList<>();

    /**
     * Instantiates a new Module.
     *
     * @param moduleCode    the module code
     * @param moduleName    the module name
     * @param lecturer      the lecturer
     * @param lengthInWeeks the length in weeks
     * @param startWeek     the start week
     */
    public Module(String moduleCode, String moduleName, Lecturer lecturer, int lengthInWeeks, int startWeek) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.lecturer = lecturer;
        this.lengthInWeeks = lengthInWeeks;
        this.startWeek = startWeek;
        this.lecturer.addModuleTaught(this);
    }

    /**
     * Gets module code.
     *
     * @return the module code
     */
    public String getModuleCode() {
        return this.moduleCode;
    }

    /**
     * Sets module code.
     *
     * @param moduleCode the module code
     */
    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    /**
     * Gets module name.
     *
     * @return the module name
     */
    public String getModuleName() {
        return this.moduleName;
    }

    /**
     * Sets module name.
     *
     * @param moduleName the module name
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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
        if (this.lecturer != null) {
            this.lecturer.removeModuleTaught(this);
        }
        this.lecturer = lecturer;
        if (lecturer != null) {
            lecturer.addModuleTaught(this);
        }
    }

    /**
     * Gets length in weeks.
     *
     * @return the length in weeks
     */
    public int getLengthInWeeks() {
        return lengthInWeeks;
    }

    /**
     * Sets length in weeks.
     *
     * @param lengthInWeeks the length in weeks
     */
    public void setLengthInWeeks(int lengthInWeeks) {
        this.lengthInWeeks = lengthInWeeks;
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
     * Gets enrolled students.
     *
     * @return the enrolled students
     */
    public List<Student> getEnrolledStudents() {
        return new LinkedList<>(enrolledStudents);
    }

    /**
     * Sets enrolled students.
     *
     * @param students the students
     */
    public void setEnrolledStudents(List<Student> students) {
        this.enrolledStudents = new LinkedList<>(students);
    }

    /**
     * Add enrolled student.
     *
     * @param student the student
     */
    public void addEnrolledStudent(Student student) {
        this.enrolledStudents.add(student);
    }

    /**
     * Remove enrolled student.
     *
     * @param student the student
     */
    public void removeEnrolledStudent(Student student) {
        this.enrolledStudents.remove(student);
    }

    /**
     * Gets sessions.
     *
     * @return the sessions
     */
    public List<Group> getSessions() {
        return new LinkedList<>(sessions);
    }

    /**
     * Sets sessions.
     *
     * @param sessions the sessions
     */
    public void setSessions(@NotNull List<Group> sessions) {
        for (Group group : sessions) {
            if (!group.getSession().getModule().equals(this)) {
                throw new IllegalArgumentException("Session module does not match this module.");
            } else if (!group.getSession().getLecturer().equals(this.lecturer)) {
                throw new IllegalArgumentException("Lecturer does not teach the module for this session.");
            }
        }
        this.sessions = new LinkedList<>(sessions);
    }

    /**
     * Add session.
     *
     * @param group the group
     */
    public void addSession(@NotNull Group group) {
        if (!group.getSession().getModule().equals(this)) {
            throw new IllegalArgumentException("Session module does not match this module.");
        } else if (!group.getSession().getLecturer().equals(this.lecturer)) {
            throw new IllegalArgumentException("Lecturer does not teach the module for this session.");
        }
        this.sessions.add(group);
    }

    /**
     * Remove session.
     *
     * @param group the group
     */
    public void removeSession(Group group) {
        this.sessions.remove(group);
    }

    @Override
    public String[] toCSVRow() {
        String sessionIDs = sessions.stream()
            .map(g -> g.getSession().getModule().getModuleCode() + ":" + g.getGroupID())
            .collect(java.util.stream.Collectors.joining(","));
        String studentIDs = enrolledStudents.stream()
            .map(s -> String.valueOf(s.getStudentID()))
            .collect(java.util.stream.Collectors.joining(","));
        return new String[]{
            moduleCode,
            moduleName,
                lecturer != null ? lecturer.getStaffID() : "", // I don't know if this is needed
            String.valueOf(lengthInWeeks),
            String.valueOf(startWeek),
            sessionIDs,
            studentIDs
        };
    }

    @Override
    public String[] getCSVHeader() {
        return new String[]{"moduleCode", "moduleName", "lecturerStaffID", "lengthInWeeks", "startWeek", "sessions", "enrolledStudents"};
    }
}
