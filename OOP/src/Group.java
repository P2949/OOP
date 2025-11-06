/*Group.java
 * 
 * Represents a group of students associated with a specific session type (Lab, Tut, or Lec). 
 */
import java.util.ArrayList;
import java.util.List;

public class Group {

    private char groupID;
    private Session Type;
    private List<Student> students = new ArrayList<>();

    public Group(char groupID, Session type) {
        this.groupID = groupID;
        setType(type);
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
        switch (Type.getClass().getSimpleName()) {
            case "Lab" -> {
                return new Lab(Type.getModule(), Type.getLecturer(), Type.getRoom());
            }
            case "Tut" -> {
                return new Tut(Type.getModule(), Type.getLecturer(), Type.getRoom());
            }
            case "Lec" -> {
                return new Lec(Type.getModule(), Type.getLecturer(), Type.getRoom());
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
    }

    public void AddStudent(Student student) {
        this.students.add(student);
    }

    public void RemoveStudent(Student student) {
        this.students.remove(student);
    }

    private void setAsLab(Lab Type) {
        this.Type = new Lab(Type.getModule(), Type.getLecturer(), Type.getRoom());
    }

    private void setAsTut(Tut Type) {
        this.Type = new Tut(Type.getModule(), Type.getLecturer(), Type.getRoom());
    }

    private void setAsLec(Lec Type) {
        this.Type = new Lec(Type.getModule(), Type.getLecturer(), Type.getRoom());
    }

}
