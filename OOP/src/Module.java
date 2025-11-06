
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
        this.lecturer.removeModuleTaught(this);
        this.lecturer = new Lecturer(lecturer.getName(), lecturer.getAge(), lecturer.getStaffID(), lecturer.getDepartment());
        this.lecturer.addModuleTaught(this);
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

    public List<Group> getSessions() {
        return new LinkedList<>(sessions);
    }

    public void setSessions(List<Group> sessions) {
        for (Group group : sessions) {
            for (Module mod : this.lecturer.getModulesTaught()) {
                if (!(mod.getModuleCode().equals(this.moduleCode) && mod.getModuleName().equals(this.moduleName))) {
                    throw new IllegalArgumentException("Session module does not match this module.");
                } else if (!(mod.getLecturer().equals(this.lecturer))) {
                    throw new IllegalArgumentException("Lecturer does not teach the module for this session.");
                }
            }
            this.sessions = new LinkedList<>(sessions);
        }
    }

    public void addSession(Group group) {
        for (Module mod : this.lecturer.getModulesTaught()) {
            if (!(mod.getModuleCode().equals(this.moduleCode) && mod.getModuleName().equals(this.moduleName))) {
                throw new IllegalArgumentException("Session module does not match this module.");
            } else if (!(mod.getLecturer().getStaffID().matches(this.lecturer.getStaffID()))) {
                throw new IllegalArgumentException("Lecturer does not teach the module for this session.");
            }
        }
        this.sessions.add(group);
    }

    public void removeSession(Group group) {
        this.sessions.remove(group);
    }
}
