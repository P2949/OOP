
public class Student extends Person {

    private String studentID;
    private Program program;
    private int yearOfStudy;

    public Student(String name, int age, String studentID, Program program, int yearOfStudy) {
        super(name, age);
        this.studentID = studentID;
        this.program = program;
        this.yearOfStudy = yearOfStudy;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
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
}
