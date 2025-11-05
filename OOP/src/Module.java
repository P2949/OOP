
import java.util.LinkedList;
import java.util.List;

public class Module {

    private int lengthInWeeks;
    private int startWeek;
    private String moduleCode;
    private String moduleName;
    private Lecturer lecturer;
    private final List<Student> enrolledStudents = new LinkedList<>();

    public Module(String moduleCode, String moduleName, Lecturer lecturer, int lengthInWeeks, int startWeek) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.lecturer = lecturer;
        this.lengthInWeeks = lengthInWeeks;
        this.startWeek = startWeek;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
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
}
