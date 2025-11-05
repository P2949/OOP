
import java.util.LinkedList;
import java.util.List;

public class Lecturer extends Staff {

    private final List<Module> modulesTaught = new LinkedList<>();

    public Lecturer(String name, int age, String staffID, String department) {
        super(name, age, staffID, department);
    }

    public List<Module> getModulesTaught() {
        return new LinkedList<>(modulesTaught);
    }

    public void addModuleTaught(Module module) {
        this.modulesTaught.add(module);
    }

}
