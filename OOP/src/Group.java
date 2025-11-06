/*Group.java
 * 
 * Represents a group of students associated with a specific session type (Lab, Tut, or Lec). 
 */
import java.util.ArrayList;
import java.util.List;

public class Group {

    private char groupID;
    private Session type;
    private List<Student> students = new ArrayList<>();

    public Group(char groupID, Session type) {
        this.groupID = groupID;
        setType(type);
        type.getModule().addSession(this);
        type.getRoom().addSession(this);
        type.getLecturer().getModulesTaught().add(type.getModule());
    }

    public Group(char groupID, Session type, List<Student> students) {
        this.groupID = groupID;
        setType(type);
        this.students = new ArrayList<>(students);
    }

    private void setType(Session type) {
        if (type instanceof Lab || type instanceof Tut || type instanceof Lec) {
            switch (type.getClass().getSimpleName()) {
                case "Lab" ->
                    setAsLab((Lab) type);
                case "Tut" ->
                    setAsTut((Tut) type);
                case "Lec" ->
                    setAsLec((Lec) type);
                default ->
                    throw new AssertionError();
            }
        } else {
            throw new IllegalArgumentException("Invalid type. Must be Lab, Tut, or Lec.");
        }
    }

    public Session getType() {
        switch (type.getClass().getSimpleName()) {
            case "Lab" -> {
                return new Lab(type.getModule(), type.getLecturer(), type.getRoom());
            }
            case "Tut" -> {
                return new Tut(type.getModule(), type.getLecturer(), type.getRoom());
            }
            case "Lec" -> {
                return new Lec(type.getModule(), type.getLecturer(), type.getRoom());
            }
            default ->
                throw new AssertionError();
        }
    }

    public char getGroupID() {
        return groupID;
    }

    public void setGroupID(char groupID) {
        this.groupID = groupID;
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public void setStudents(List<Student> students) {
        this.students = new ArrayList<>(students);
        Session tempType = type;
        List<Student> tempStudents = new ArrayList<>(this.students);
        tempType.getModule().setEnrolledStudents(tempStudents);
        setType(type);
    }

    public void addStudent(Student student) {
        type.getModule().addEnrolledStudent(student);
        this.students.add(student);
    }

    public void removeStudent(Student student) {
        type.getModule().removeEnrolledStudent(student);
        this.students.remove(student);
    }

    private void setAsLab(Lab type) {
        this.type = new Lab(type.getModule(), type.getLecturer(), type.getRoom());
        type.getRoom().addSession(this);
        type.getModule().addSession(this);
        type.getLecturer().addModuleTaught(type.getModule());
    }

    private void setAsTut(Tut type) {
        this.type = new Tut(type.getModule(), type.getLecturer(), type.getRoom());
        type.getRoom().addSession(this);
        type.getModule().addSession(this);
        type.getLecturer().addModuleTaught(type.getModule());
    }

    private void setAsLec(Lec type) {
        this.type = new Lec(type.getModule(), type.getLecturer(), type.getRoom());
        type.getRoom().addSession(this);
        type.getModule().addSession(this);
        type.getLecturer().addModuleTaught(type.getModule());
    }

}
