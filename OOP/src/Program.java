
import java.util.LinkedList;
import java.util.List;

public class Program {

    private final List<Module> modules = new LinkedList<>();
    private int totalModules;

    public Program(int totalModules) {
        this.totalModules = totalModules;
        while (modules.size() < totalModules) {
            modules.add(null);
        }
    }

    public int getTotalModules() {
        return totalModules;
    }

    public void setTotalModules(int totalModules) {
        this.totalModules = totalModules;
    }

    public List<Module> getModulesTaught() {
        return new LinkedList<>(modules);

    }

    public void addModuleTaught(Module module) {
        this.modules.add(module);
    }

}
