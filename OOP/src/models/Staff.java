/* Staff.java
 *
 * This class represents a staff member, which is a subclass of Person, with additional attributes such as staff ID and department.
 */
package models;
public abstract class Staff extends Person {

    private String staffID;
    private String department;

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

}
