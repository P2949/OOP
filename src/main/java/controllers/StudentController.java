package controllers;

import models.*;
import models.Module;
import views.View;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * The type Student controller.
 */
public class StudentController {

    /**
     * The View.
     */
    final View view;
    /**
     * The Student.
     */
    final Student student;
    /**
     * The Db controller.
     */
    final DatabaseController dbController = new DatabaseController();

    /**
     * Instantiates a new Student controller.
     *
     * @param student the student
     * @param view    the view
     */
    public StudentController(Student student, View view) {
        this.student = student;
        this.view = view;
    }

    /**
     * Gets modules.
     *
     * @return the modules
     */
    public List<Module> getModules() {
        return new ArrayList<>(student.getProgram().getModulesTaught());
    }

    /**
     * Gets laboratories.
     *
     * @return the laboratories
     */
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

    /**
     * Gets tutorials.
     *
     * @return the tutorials
     */
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

    /**
     * Gets lectures.
     *
     * @return the lectures
     * @throws Exception the exception
     */
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


    /**
     * Gets the current student.
     *
     * @return the current student
     */
    public Student getCurrentStudent() {
        return this.student;
    }
}
