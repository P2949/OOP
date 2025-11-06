
/* Module.java
 *
 * Represents a module in the system.
 * Contains attributes such as length in weeks, start week, module code, module name, lecturer, and enrolled students.
 */
import java.util.LinkedList;
import java.util.List;

public class Module {

    private int lengthInWeeks;
    private int startWeek;
    private String moduleCode;
    private String moduleName;
    private Lecturer lecturer;
    private List<Session> sessions = new LinkedList<>();
    private List<Student> enrolledStudents = new LinkedList<>();

    public Module(String moduleCode, String moduleName, Lecturer lecturer, int lengthInWeeks, int startWeek) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.lecturer = lecturer;
        this.lengthInWeeks = lengthInWeeks;
        this.startWeek = startWeek;
    }

    public String getModuleCode() {
        String tempModuleCode = this.moduleCode;
        return tempModuleCode;
    }

    public void setModuleCode(String moduleCode) {
        String tempModuleCode = this.moduleCode;
        this.moduleCode = tempModuleCode;
    }

    public String getModuleName() {
        String tempModuleName = this.moduleName;
        return tempModuleName;
    }

    public void setModuleName(String moduleName) {
        String tempModuleName = this.moduleName;
        this.moduleName = tempModuleName;
    }

    public Lecturer getLecturer() {
        return new Lecturer(this.lecturer.getName(), this.lecturer.getAge(), this.lecturer.getStaffID(), this.lecturer.getDepartment());
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = new Lecturer(lecturer.getName(), lecturer.getAge(), lecturer.getStaffID(), lecturer.getDepartment());
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

    public void addEnrolledStudent(Student student) {
        this.enrolledStudents.add(student);
    }

    public void removeEnrolledStudent(Student student) {
        this.enrolledStudents.remove(student);
    }

    public void setEnrolledStudents(List<Student> students) {
        this.enrolledStudents = new LinkedList<>(students);
    }

    public List<Session> getSessions() {
        return new LinkedList<>(sessions);
    }

    public void setSessions(List<Session> sessions) {
        for (Session session : sessions) {
            if (this.lecturer.getModulesTaught().contains(session.getModule()) == false) {
                throw new IllegalArgumentException("Lecturer does not teach the module for this session.");
            } else if (this.moduleCode.equals(session.getModule().getModuleCode()) == false) {
                throw new IllegalArgumentException("Session module does not match this module.");
            }
        }
        this.sessions = new LinkedList<>(sessions);
    }

    public void addSession(Session session) {
        if (this.lecturer.getModulesTaught().contains(session.getModule()) == false) {
            throw new IllegalArgumentException("Lecturer does not teach the module for this session.");
        } else if (this.moduleCode.equals(session.getModule().getModuleCode()) == false) {
            throw new IllegalArgumentException("Session module does not match this module.");
        }
        this.sessions.add(session);
    }

    public void removeSession(Session session) {
        this.sessions.remove(session);
    }
}
