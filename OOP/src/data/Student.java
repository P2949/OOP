/* Student.java
 *
 * This class represents a student, which is a subclass of Person, with additional attributes such as student ID, program, year of study, and lab groups.
 */
package data;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Student extends Person {

    private static int studentCounter;
    private int studentID;
    private Program program;
    private int yearOfStudy;
    private final List<Group> groups = new LinkedList<>();

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
        return this.program;
    }

    public void setProgram(Program program) {
        if (program.getModulesTaught().isEmpty()) {
            throw new IllegalArgumentException("Cannot set program: no modules taught.");
        } else {
            for (Module module : program.getModulesTaught()) {
                boolean enrolled = false;
                for (Group group : groups) {
                    if (group.getSession().getModule().equals(module)) {
                        enrolled = true;
                        break;
                    }
                }
                if (!enrolled) {
                    throw new IllegalArgumentException("Cannot set program: student not enrolled in all modules.");
                }
            }
        }
        for (Module module : program.getModulesTaught()) {
            module.addEnrolledStudent(this);
        }
        this.program = program;
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
        if (!group.getSession().getModule().getEnrolledStudents().contains(this)) {
            throw new IllegalArgumentException("Cannot add group: either already on that group or not enrolled in the module.");
        }
        // Check if already in this exact group
        if (this.groups.contains(group)) {
            return; // Already in this group, nothing to do
        }
        // Check if already in a different group with same ID for the same session
        for (Group g : this.groups) {
            if (g.getGroupID() == group.getGroupID() && g.getSession().equals(group.getSession())) {
                throw new IllegalArgumentException("Cannot add group: already on that group.");
            }
        }
        this.groups.add(group);
        if (!group.getStudents().contains(this)) {
            group.addStudent(this);
        }
    }

    public void removeGroup(Group group) {
        if (this.groups.remove(group)) {
            if (group.getStudents().contains(this)) {
                group.removeStudent(this);
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
