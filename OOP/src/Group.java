
import java.util.ArrayList;
import java.util.List;

public class Group {

    private char groupID;
    private Session Type;
    private List<Student> students = new ArrayList<>();

    public Group(char groupID, List<Student> students) {
        this.groupID = groupID;
        this.students = students;
    }

    public void setType(Session type) {
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

    public char getGroupID() {
        return groupID;
    }

    public void setGroupID(char groupID) {
        this.groupID = groupID;
    }

    private void setAsLab(Lab Type) {
        this.Type = (Lab) Type;
    }

    private void setAsTut(Tut Type) {
        this.Type = (Tut) Type;
    }

    private void setAsLec(Lec Type) {
        this.Type = (Lec) Type;
    }

}
