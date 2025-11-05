
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

    public char getGroupID() {
        return groupID;
    }

    public void setGroupID(char groupID) {
        this.groupID = groupID;
    }

    public void setAsLab(Lab Type) {
        this.Type = Type;
    }

    public void setAsTut(Tut Type) {
        this.Type = Type;
    }

    public void setAsLec(Lec Type) {
        this.Type = Type;
    }

}
