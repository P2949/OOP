package controllers;

import models.Laboratory;
import models.Module;
import models.Session;
import models.Student;
import views.View;
import java.util.List;
import java.util.ArrayList;
public class AdminController {

    View view;

    public AdminController(View view){
        this.view = view;
    }

    public Student getStudent(Student student) {
        return student;
    }

    public Student CreateStudent() {
        return null;
    }

    public Student UpdateStudent(Student student) {
        return null;
    }

    public Student DeleteStudent(Student student) {
        return null;
    }

    public List<Student> getAllStudents() {
        return null;
    }

    public List<Student> getStudentsByName(String firstName, String lastName) {
        return null;
    }

    public List<Module> getAllModules() {
        return null;
    }

    public List<Session> getAllSessionsForModule(Module module) {
        return null;
    }

    public Laboratory createLaboratory(Laboratory laboratory) {
        return null;
    }

    public Laboratory updateLaboratory(Laboratory laboratory) {
        return null;
    }

    public Laboratory DeleteLaboratory(Laboratory laboratory) {
        return null;
    }

    public List<Laboratory> getAllLaboratories() {
        return null;
    }
}

