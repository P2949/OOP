package controllers;

import models.*;
import models.Module;
import views.View;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class StudentController {

    View view;
    Student student;
    DatabaseController dbController = new DatabaseController();

    public StudentController(Student student, View view) {
        this.student = student;
        this.view = view;
    }

    public List<Module> getModules() {
        return new ArrayList<>(student.getProgram().getModulesTaught());
    }

    public List<Laboratory> getLaboratories()
    {
        List<Laboratory> lab = new LinkedList<>();
        ListIterator<Group> groupIterator = this.student.getGroups().listIterator();
        while (groupIterator.hasNext()) {
            if (groupIterator.next().getSession().getClass().equals(Laboratory.class)) {
                lab.add((Laboratory) groupIterator.next().getSession());
            }
        }
        return new ArrayList<>(lab);
    }

    public List<Tutorial> getTutorials() {
        List<Tutorial> tut = new LinkedList<>();
        ListIterator<Group> groupIterator = this.student.getGroups().listIterator();
        while (groupIterator.hasNext()) {
            if (groupIterator.next().getSession().getClass().equals(Tutorial.class)) {
                tut.add((Tutorial) groupIterator.next().getSession());
            }
        }
        return new ArrayList<>(tut);
    }

    public List<Lecture> getLectures() throws Exception {
        if (this.student.getGroups().isEmpty()) {
            int x = dbController.Search(Path.of("./csv/Group.csv"), String.valueOf(this.student.getStudentID()));
            if (x == -1) throw new Exception("Student not found");
            else {
                // I still have no idea how we are going to be saving data to the csv files,
                // so i'm not really able to write code to parse it, and load it.
                //TODO figure this out ;(
            }
        }
        List<Lecture> lec = new LinkedList<>();
        ListIterator<Group> groupIterator = this.student.getGroups().listIterator();
        while (groupIterator.hasNext()) {
            if (groupIterator.next().getSession().getClass().equals(Lecture.class)) {
                lec.add((Lecture) groupIterator.next().getSession());
            }
        }
        return new ArrayList<>(lec);
    }



    public Student getCurrentStudent() {
        return this.student;
    }
}
