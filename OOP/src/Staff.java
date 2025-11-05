
import java.util.LinkedList;
import java.util.List;

public class Staff extends Person {

    private String staffID;
    private String department;
    private List<Module> modulesTaught = new LinkedList<>();

    public Staff(String name, int age, String staffID, String department) {
        super(name, age);
        this.staffID = staffID;
        this.department = department;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Module> getModulesTaught() {
        List<Module> modulesTaughttemp = new LinkedList<>();
        modulesTaughttemp.addAll(modulesTaught);
        return modulesTaughttemp;
    }

    public void addModuleTaught(Module module) {
        this.modulesTaught.add(module);
    }

}
