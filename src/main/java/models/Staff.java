/* Staff.java
 *
 * This class represents a staff member, which is a subclass of Person, with additional attributes such as staff ID and department.
 */
package models;

/**
 * The type Staff.
 */
public abstract class Staff extends Person {

    private String staffID;
    private String department;

    /**
     * Instantiates a new Staff.
     *
     * @param name       the name
     * @param age        the age
     * @param staffID    the staff id
     * @param department the department
     */
    public Staff(String name, int age, String staffID, String department) {
        super(name, age);
        this.staffID = staffID;
        this.department = department;
    }

    /**
     * Gets staff id.
     *
     * @return the staff id
     */
    public String getStaffID() {
        return staffID;
    }

    /**
     * Sets staff id.
     *
     * @param staffID the staff id
     */
    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    /**
     * Gets department.
     *
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets department.
     *
     * @param department the department
     */
    public void setDepartment(String department) {
        this.department = department;
    }

}
