import java.time.LocalDate;

public class Student extends Person {

    private int studentID;
    private Program program;
    private int yearOfStudy;
    private char labGroup;

    public Student(String name, int age, String studentID, Program program, int yearOfStudy) {
        super(name, age);
        this.studentID = LocalDate.now().getYear();
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
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public char getLabGroup() {
        return labGroup;
    }

    public void setLabGroup(char labGroup) {
        this.labGroup = labGroup;
    }
}
