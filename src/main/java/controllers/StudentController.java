package controllers;

import models.Group;
import models.Laboratory;
import models.Lecture;
import models.Module;
import models.Student;
import models.Tutorial;
import views.StudentView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentController {
    private final Student student;
    private final StudentView view;
    private final Scanner scanner = new Scanner(System.in);

    public StudentController(Student student, StudentView view) {
        this.student = student;
        this.view = view;
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Student View: " + student.getName() + " ===");
            System.out.println("1. View Timetable");
            System.out.println("2. View Modules");
            System.out.println("3. View Lectures");
            System.out.println("4. View Tutorials");
            System.out.println("5. View Laboratories");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    view.displayTimeTable(student);
                    break;
                case 2:
                    view.displayModules(getModules());
                    break;
                case 3:
                    view.displayLectures(getLectures());
                    break;
                case 4:
                    view.displayTutorials(getTutorials());
                    break;
                case 5:
                    view.displayLaboratories(getLaboratories());
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    view.showMessage("Invalid option");
                    break;
            }
        }
    }

    public List<Module> getModules() {
        if (student.getProgram() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(student.getProgram().getModulesTaught());
    }

    public List<Laboratory> getLaboratories() {
        List<Laboratory> labs = new ArrayList<>();
        for (Group group : student.getGroups()) {
            if (group.getSession() instanceof Laboratory) {
                labs.add((Laboratory) group.getSession());
            }
        }
        return labs;
    }

    public List<Tutorial> getTutorials() {
        List<Tutorial> tuts = new ArrayList<>();
        for (Group group : student.getGroups()) {
            if (group.getSession() instanceof Tutorial) {
                tuts.add((Tutorial) group.getSession());
            }
        }
        return tuts;
    }

    public List<Lecture> getLectures() {
        List<Lecture> lectures = new ArrayList<>();
        if (student.getProgram() != null) {
            for (Module module : student.getProgram().getModulesTaught()) {
                for (Group group : module.getSessions()) {
                    if (group.getSession() instanceof Lecture) {
                        lectures.add((Lecture) group.getSession());
                    }
                }
            }
        }
        return lectures;
    }

    public Student getCurrentStudent() {
        return student;
    }
}
