
import java.util.ArrayList;
import java.util.List;

public class Group {

    private char groupID;
    private Module module;
    private List<Student> students = new ArrayList<>();

    public Group(char groupID, Module module, List<Student> students) {
        this.groupID = groupID;
        this.module = module;
        this.students = students;
    }

    public char getGroupID() {
        return groupID;
    }

    public void setGroupID(char groupID) {
        this.groupID = groupID;
    }
}
