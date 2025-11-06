/* Student.java
 *
 * This class represents a student, which is a subclass of Person, with additional attributes such as student ID, program, year of study, and lab groups.
 */
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Student extends Person {

    private int studentID;
    private Program program;
    private int yearOfStudy;
    private List<Group> groups = new LinkedList<>();
    private static int studentCounter;

    public Student(String name, int age, Program program, int yearOfStudy) {
        super(name, age);
        studentCounter++;
        this.studentID = LocalDate.now().getYear() + studentCounter;
        this.program = program;
        this.yearOfStudy = yearOfStudy;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public Program getProgram() {
        Program temp = new Program(program.getTotalModules());
        temp.setModulesTaught(program.getModulesTaught());
        return temp;
    }

    public void setProgram(Program program) {
        if (program.getModulesTaught().isEmpty()) {
            throw new IllegalArgumentException("Cannot set program: no modules taught.");
        } else {
            for (Module module : program.getModulesTaught()) {
                boolean enrolled = false;
                for (Group group : groups) {
                    if (group.getType().getModule().equals(module)) {
                        enrolled = true;
                        break;
                    }
                }
                if (!enrolled) {
                    throw new IllegalArgumentException("Cannot set program: student not enrolled in all modules.");
                }
            }
        }
        Program temp = new Program(program.getTotalModules());
        temp.setModulesTaught(program.getModulesTaught());
        this.program = temp;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public List<Group> getGroups() {
        return new LinkedList<>(groups);
    }

    public void addGroup(Group group) {
        if (!this.groups.contains(group) && group.getType().getModule().getEnrolledStudents().contains(this)) {
            this.groups.add(group);
        } else {
            throw new IllegalArgumentException("Cannot add group: either already enrolled or not enrolled in the module.");
        }
    }

    public void removeGroup(Group group) {
        this.groups.remove(group);
    }

    public void setGroups(List<Group> groups) {
        for (Group group : groups) {
            if (group.getType().getModule().getEnrolledStudents().contains(this)) {
                this.groups = new LinkedList<>(groups);
            } else {
                throw new IllegalArgumentException("Cannot set groups: student not enrolled in one or more module(s).");
            }
        }
    }

    public void clearGroups() {
        this.groups.clear();
    }

    public int getStudentCount() {
        return studentCounter;
    }
}
