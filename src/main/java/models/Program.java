
/* Program.java
 *
 * Represents a program of study with attributes such as total modules and a list of modules taught.
 */
package models;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Program.
 */
public class Program implements CSVModel {

    private List<Module> modules = new LinkedList<>();
    private int totalModules;
    private String programName;

    /**
     * Instantiates a new Program.
     *
     * @param programName  the program name
     * @param totalModules the total modules
     */
    public Program(String programName, int totalModules) {
        this.programName = programName;
        this.totalModules = totalModules;
    }

    /**
     * Gets total modules.
     *
     * @return the total modules
     */
    public int getTotalModules() {
        return totalModules;
    }

    /**
     * Sets total modules.
     *
     * @param totalModules the total modules
     */
    public void setTotalModules(int totalModules) {
        this.totalModules = totalModules;
    }

    /**
     * Gets modules taught.
     *
     * @return the modules taught
     */
    public List<Module> getModulesTaught() {
        return new LinkedList<>(modules);
    }

    /**
     * Sets modules taught.
     *
     * @param modules the modules
     */
    public void setModulesTaught(List<Module> modules) {
        this.modules = new LinkedList<>(modules);
    }

    /**
     * Add module taught.
     *
     * @param module the module
     */
    public void addModuleTaught(Module module) {
        this.modules.add(module);
    }

    /**
     * Gets program name.
     *
     * @return the program name
     */
    public String getProgramName() {
        return programName;
    }

    /**
     * Sets program name.
     *
     * @param programName the program name
     */
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
