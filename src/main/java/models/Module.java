
/* Module.java
 *
 * Represents a module in the system.
 * Contains attributes such as length in weeks, start week, module code, module name, lecturer, and enrolled students.
 */
package models;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class Module implements CSVModel {

    private int lengthInWeeks;
    private int startWeek;
    private String moduleCode;
    private String moduleName;
    private Lecturer lecturer;
    private List<Group> sessions = new LinkedList<>();
    private List<Student> enrolledStudents = new LinkedList<>();

    public Module(String moduleCode, String moduleName, Lecturer lecturer, int lengthInWeeks, int startWeek) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.lecturer = lecturer;
        this.lengthInWeeks = lengthInWeeks;
        this.startWeek = startWeek;
        this.lecturer.addModuleTaught(this);
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        if (this.lecturer != null) {
            this.lecturer.removeModuleTaught(this);
        }
        this.lecturer = lecturer;
        if (lecturer != null) {
            lecturer.addModuleTaught(this);
        }
    }

    public int getLengthInWeeks() {
        return lengthInWeeks;
    }

    public void setLengthInWeeks(int lengthInWeeks) {
        this.lengthInWeeks = lengthInWeeks;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public List<Student> getEnrolledStudents() {
        return new LinkedList<>(enrolledStudents);
    }

    public void setEnrolledStudents(List<Student> students) {
        this.enrolledStudents = new LinkedList<>(students);
    }

    public void addEnrolledStudent(Student student) {
        this.enrolledStudents.add(student);
    }

    public void removeEnrolledStudent(Student student) {
        this.enrolledStudents.remove(student);
    }

    public List<Group> getSessions() {
        return new LinkedList<>(sessions);
    }

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

    public void addSession(@NotNull Group group) {
        if (!group.getSession().getModule().equals(this)) {
            throw new IllegalArgumentException("Session module does not match this module.");
        } else if (!group.getSession().getLecturer().equals(this.lecturer)) {
            throw new IllegalArgumentException("Lecturer does not teach the module for this session.");
        }
        this.sessions.add(group);
    }

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
            lecturer != null ? lecturer.getStaffID() : "", // i dont know if this is needed
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
