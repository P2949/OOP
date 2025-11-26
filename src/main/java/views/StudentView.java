package views;

import models.*;
import models.Module;

import java.util.List;

public interface StudentView {
    void displayTimeTable(Student student);
    void displayModules(List<Module> modules);
    void displayLaboratories(List<Laboratory> laboratories);
    void displayTutorials(List<Tutorial> tutorials);
    void displayLectures(List<Lecture> lectures);
    void showMessage(String message);
}
