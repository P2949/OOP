package controllers;

import models.Laboratory;
import models.Module;
import models.Session;
import models.Student;
import views.View;
import java.util.List;

/**
 * The type Admin controller.
 */
public class AdminController {

    /**
     * The View.
     */
    final View view;

    /**
     * Instantiates a new Admin controller.
     *
     * @param view the view
     */
    public AdminController(View view){
        this.view = view;
    }

    /**
     * Gets student.
     *
     * @param student the student
     * @return the student
     */
    public Student getStudent(Student student) {
        return student;
    }

    /**
     * Create student.
     *
     * @return the student
     */
    public Student CreateStudent() {
        return null;
    }

    /**
     * Update student.
     *
     * @param student the student
     * @return the student
     */
    public Student UpdateStudent(Student student) {
        return null;
    }

    /**
     * Delete student.
     *
     * @param student the student
     * @return the student
     */
    public Student DeleteStudent(Student student) {
        return null;
    }

    /**
     * Gets all students.
     *
     * @return the all students
     */
    public List<Student> getAllStudents() {
        return null;
    }

    /**
     * Gets students by name.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the students by name
     */
    public List<Student> getStudentsByName(String firstName, String lastName) {
        return null;
    }

    /**
     * Gets all modules.
     *
     * @return the all modules
     */
    public List<Module> getAllModules() {
        return null;
    }

    /**
     * Gets all sessions for module.
     *
     * @param module the module
     * @return the all sessions for module
     */
    public List<Session> getAllSessionsForModule(Module module) {
        return null;
    }

    /**
     * Create a laboratory.
     *
     * @param laboratory the laboratory
     * @return the laboratory
     */
    public Laboratory createLaboratory(Laboratory laboratory) {
        return null;
    }

    /**
     * Update laboratory.
     *
     * @param laboratory the laboratory
     * @return the laboratory
     */
    public Laboratory updateLaboratory(Laboratory laboratory) {
        return null;
    }

    /**
     * Delete laboratory.
     *
     * @param laboratory the laboratory
     * @return the laboratory
     */
    public Laboratory DeleteLaboratory(Laboratory laboratory) {
        return null;
    }

    /**
     * Gets all laboratories.
     *
     * @return the all laboratories
     */
    public List<Laboratory> getAllLaboratories() {
        return null;
    }
}

