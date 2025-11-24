package views;

import models.Group;
import models.Laboratory;
import models.Lecture;
import models.Module;
import models.Student;
import models.Tutorial;

import java.util.ArrayList;
import java.util.List;

public class StudentConsoleView implements StudentView {

    @Override
    public void displayTimeTable(Student student) {
        System.out.println("\n=== Timetable for " + student.getName() + " ===");
        System.out.println("Student ID: " + student.getStudentID());
        System.out.println("Course: " + (student.getProgram() != null ? student.getProgram().getProgramName() : "None"));
        System.out.println("Year: " + student.getYearOfStudy());
        System.out.println();
        
        List<Lecture> lectures = new ArrayList<>();
        List<Tutorial> tutorials = new ArrayList<>();
        List<Laboratory> laboratories = new ArrayList<>();
        
        for (Group group : student.getGroups()) {
            if (group.getSession() instanceof Lecture) {
                lectures.add((Lecture) group.getSession());
            } else if (group.getSession() instanceof Tutorial) {
                tutorials.add((Tutorial) group.getSession());
            } else if (group.getSession() instanceof Laboratory) {
                laboratories.add((Laboratory) group.getSession());
            }
        }
        
        if (lectures.isEmpty() && tutorials.isEmpty() && laboratories.isEmpty()) {
            System.out.println("No timetable entries found. Student may not be assigned to any groups.");
            return;
        }
        
        displayLectures(lectures);
        displayTutorials(tutorials);
        displayLaboratories(laboratories);
    }

    @Override
    public void displayModules(List<Module> modules) {
        if (modules.isEmpty()) {
            System.out.println("No modules found.");
            return;
        }
        System.out.println("\n=== Modules ===");
        for (Module module : modules) {
            System.out.println("Code: " + module.getModuleCode() + 
                             " | Name: " + module.getModuleName() + 
                             " | Lecturer: " + (module.getLecturer() != null ? module.getLecturer().getName() : "N/A") +
                             " | Weeks: " + module.getLengthInWeeks() +
                             " | Start Week: " + module.getStartWeek());
        }
    }

    @Override
    public void displayLaboratories(List<Laboratory> laboratories) {
        if (laboratories.isEmpty()) {
            return;
        }
        System.out.println("\n=== Laboratories ===");
        for (Laboratory lab : laboratories) {
            System.out.println("Module: " + lab.getModule().getModuleCode() +
                             " | Day: " + lab.getDay() +
                             " | Time: " + lab.getStartTime() + " - " + lab.getEndTime() +
                             " | Room: " + lab.getRoom().getRoomNumber() +
                             " | Weeks: " + lab.getStartWeek() + "-" + lab.getEndWeek());
        }
    }

    @Override
    public void displayTutorials(List<Tutorial> tutorials) {
        if (tutorials.isEmpty()) {
            return;
        }
        System.out.println("\n=== Tutorials ===");
        for (Tutorial tut : tutorials) {
            System.out.println("Module: " + tut.getModule().getModuleCode() +
                             " | Day: " + tut.getDay() +
                             " | Time: " + tut.getStartTime() + " - " + tut.getEndTime() +
                             " | Room: " + tut.getRoom().getRoomNumber() +
                             " | Weeks: " + tut.getStartWeek() + "-" + tut.getEndWeek());
        }
    }

    @Override
    public void displayLectures(List<Lecture> lectures) {
        if (lectures.isEmpty()) {
            return;
        }
        System.out.println("\n=== Lectures ===");
        for (Lecture lec : lectures) {
            System.out.println("Module: " + lec.getModule().getModuleCode() +
                             " | Day: " + lec.getDay() +
                             " | Time: " + lec.getStartTime() + " - " + lec.getEndTime() +
                             " | Room: " + lec.getRoom().getRoomNumber() +
                             " | Weeks: " + lec.getStartWeek() + "-" + lec.getEndWeek());
        }
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}
