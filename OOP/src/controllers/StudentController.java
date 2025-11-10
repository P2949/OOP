package controllers;

import models.Student;
import models.Module;
import models.Laboratory;
import views.View;

import java.util.ArrayList;
import java.util.List;

public class StudentController {

    View view;
    Student student;

    public StudentController(Student student, View view) {
        this.student = student;
        this.view = view;
    }

    public List<Module> getModules() {
        return new ArrayList<>();
    }

    public List<Laboratory> getLaboratories() {
        return new ArrayList<>();
    }

    public Student getCurrentStudent() {
        return this.student;
    }
}
