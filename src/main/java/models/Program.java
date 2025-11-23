
/* Program.java
 *
 * Represents a program of study with attributes such as total modules and a list of modules taught.
 */
package models;
import java.util.LinkedList;
import java.util.List;

public class Program implements CSVModel {

    private List<Module> modules = new LinkedList<>();
    private int totalModules;
    private String programName;

    public Program(String programName, int totalModules) {
        this.programName = programName;
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

    public void setModulesTaught(List<Module> modules) {
        this.modules = new LinkedList<>(modules);
    }

    public void addModuleTaught(Module module) {
        this.modules.add(module);
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    @Override
    public String[] toCSVRow() {
        String moduleCodes = modules.stream()
            .map(Module::getModuleCode)
            .collect(java.util.stream.Collectors.joining(","));
        return new String[]{
            programName,
            String.valueOf(totalModules),
            moduleCodes
        };
    }

    @Override
    public String[] getCSVHeader() {
        return new String[]{"programName", "totalModules", "moduleCodes"};
    }
}
