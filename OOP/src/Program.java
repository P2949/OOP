
/* Program.java
 * 
 * Represents a program of study with attributes such as total modules and a list of modules taught.
 */
import java.util.LinkedList;
import java.util.List;

public class Program {

    private List<Module> modules = new LinkedList<>();
    private int totalModules;

    public Program(int totalModules) {
        this.totalModules = totalModules;
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

    public void setModulesTaught(List<Module> modules) {
        this.modules = new LinkedList<>(modules);
    }

}
