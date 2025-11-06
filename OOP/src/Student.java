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
        for (Module module : temp.getModulesTaught()) {
            module.addEnrolledStudent(this);
        }
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
        for (Student s : group.getType().getModule().getEnrolledStudents()) {
            if (s.getStudentID() == this.studentID) {
                for (Group g : s.getGroups()) {
                    if (g.getGroupID() == group.getGroupID()) {
                        throw new IllegalArgumentException("Cannot add group: already on that group.");
                    } else {
                        this.groups.add(group);
                        break;
                    }
                }
            } else {
                throw new IllegalArgumentException("Cannot add group: either already on that group or not enrolled in the module.");
            }
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

    public static void main(String[] args) {
        Program p1 = new Program(3);
        Room r1 = new Room("Lab Room 1", 30, "classroom", "CS Building");
        Lecturer l1 = new Lecturer("Dr. Smith", 45, "L001", "Computer Science");
        Module m1 = new Module("CS101", "Intro to CS", l1, 12, 1);
        p1.addModuleTaught(m1);
        Student s1 = new Student("Alice", 20, p1, 1);
        m1.addEnrolledStudent(s1);
        Lab l = new Lab(m1, l1, r1);
        Group g1 = new Group('A', l);
        s1.addGroup(g1);
        System.out.println("Student " + s1.getName() + " enrolled in program with " + s1.getProgram().getModulesTaught().size() + " module(s).");
    }
}
