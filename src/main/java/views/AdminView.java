package views;

import models.Program;
import models.Student;

import java.util.List;

public interface AdminView {
    int showMenu();

    Student addStudent();

    Program createCourse();

    String selectProgram(List<Program> programs);

    int selectStudent(List<Student> students);

    void showMessage(String message);
}
