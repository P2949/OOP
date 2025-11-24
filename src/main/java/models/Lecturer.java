
/* Lecturer.java
 *
 * This class represents a lecturer, which is a subclass of Staff, with additional attributes such as modules taught.
 */
package models;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Lecturer.
 */
public class Lecturer extends Staff implements CSVModel {

    private final List<Module> modulesTaught = new LinkedList<>();

    /**
     * Instantiates a new Lecturer.
     *
     * @param name       the name
     * @param age        the age
     * @param staffID    the staff id
     * @param department the department
     */
    public Lecturer(String name, int age, String staffID, String department) {
        super(name, age, staffID, department);
    }

    /**
     * Gets modules taught.
     *
     * @return the modules taught
     */
    public List<Module> getModulesTaught() {
        return new LinkedList<>(modulesTaught);
    }

    /**
     * Add module taught.
     *
     * @param module the module
     */
    public void addModuleTaught(Module module) {
        this.modulesTaught.add(module);
    }

    /**
     * Remove module taught.
     *
     * @param module the module
     */
    public void removeModuleTaught(Module module) {
        this.modulesTaught.remove(module);
    }

    @Override
    public String[] toCSVRow() {
        String moduleCodes = modulesTaught.stream()
            .map(Module::getModuleCode)
            .collect(java.util.stream.Collectors.joining(","));
        return new String[]{
            getStaffID(),
            getName(),
            String.valueOf(getAge()),
            getDepartment(),
            moduleCodes
        };
    }

    @Override
    public String[] getCSVHeader() {
        return new String[]{"staffID", "name", "age", "department", "modulesTaught"};
    }
}
