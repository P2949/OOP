/* Student.java
 *
 * This class represents a student, which is a subclass of Person, with additional attributes such as student ID, program, year of study, and lab groups.
 */
package models;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Student.
 */
public class Student extends Person implements CSVModel {

    private static int studentCounter;
    private int studentID;
    private Program program;
    private int yearOfStudy;
    private final List<Group> groups = new LinkedList<>();

    /**
     * Instantiates a new Student.
     *
     * @param name        the name
     * @param age         the age
     * @param program     the program
     * @param yearOfStudy the year of study
     */
    public Student(String name, int age, Program program, int yearOfStudy) {
        super(name, age);
        studentCounter++;
        this.studentID = LocalDate.now().getYear() + studentCounter;
        this.program = program;
        this.yearOfStudy = yearOfStudy;
    }

    /**
     * Gets a student id.
     *
     * @return the student id
     */
    public int getStudentID() {
        return studentID;
    }

    /**
     * Sets student id.
     *
     * @param studentID the student id
     */
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    /**
     * Gets program.
     *
     * @return the program
     */
    public Program getProgram() {
        return this.program;
    }

    /**
     * Sets program.
     *
     * @param program the program
     */
    public void setProgram(@NotNull Program program) {
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

    /**
     * Gets year of study.
     *
     * @return the year of study
     */
    public int getYearOfStudy() {
        return yearOfStudy;
    }

    /**
     * Sets year of study.
     *
     * @param yearOfStudy the year of study
     */
    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    /**
     * Gets groups.
     *
     * @return the groups
     */
    public List<Group> getGroups() {
        return new LinkedList<>(groups);
    }

    /**
     * Add group.
     *
     * @param group the group
     */
    public void addGroup(@NotNull Group group) {
        if (!group.getSession().getModule().getEnrolledStudents().contains(this)) {
            throw new IllegalArgumentException("Cannot add group: either already on that group or not enrolled in the module.");
        }
        // Check if already in this exact group
        if (this.groups.contains(group)) {
            return; // Already in this group, nothing to do
        }
        // Check if already in a different group with the same ID for the same session
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

    /**
     * Remove group.
     *
     * @param group the group
     */
    public void removeGroup(Group group) {
        if (this.groups.remove(group)) {
            if (group.getStudents().contains(this)) {
                group.removeStudent(this);
            }
        }
    }

    /**
     * Clear groups.
     */
    public void clearGroups() {
        this.groups.clear();
    }

    /**
     * Gets student count.
     *
     * @return the student count
     */
    public int getStudentCount() {
        return studentCounter;
    }

    @Override
    public String[] toCSVRow() {
        String groupIDs = groups.stream()
            .map(g -> {
                Session s = g.getSession();
                return g.getGroupID() + ":" +
                       s.getClass().getSimpleName() + ":" +
                       s.getModule().getModuleCode() + ":" +
                       s.getDay() + ":" +
                       s.getStartTime() + ":" +
                       s.getRoom().getRoomNumber();
            })
            .collect(java.util.stream.Collectors.joining(","));
        return new String[]{
            String.valueOf(studentID),
            getName(),
            String.valueOf(getAge()),
            program != null ? program.getProgramName() : "",
            String.valueOf(yearOfStudy),
            groupIDs
        };
    }

    @Override
    public String[] getCSVHeader() {
        return new String[]{"studentID", "name", "age", "programName", "yearOfStudy", "groups"};
    }
}
