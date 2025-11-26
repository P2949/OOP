package controllers;

import models.*;
import models.Module;
import views.AdminView;
import views.StudentConsoleView;

import java.util.*;

public class AdminController {
    private final AdminView view;
    private final DatabaseController dbController = new DatabaseController();
    private final List<Student> students = new ArrayList<>();
    private final List<Lecturer> lecturers = new ArrayList<>();
    private final List<Program> programs = new ArrayList<>();
    private final List<Module> modules = new ArrayList<>();
    private final List<Session> sessions = new ArrayList<>();
    private final List<Group> groups = new ArrayList<>();

    public AdminController(AdminView view) {

        this.view = view;
        loadExistingData(); // Load data on startup
    }

    public void start() {
        boolean running = true;
        while (running) {
            int choice = view.showMenu();
            
            switch (choice) {
                case 1:
                    createCourse();
                    break;
                case 2:
                    createStudent();
                    break;
                case 3:
                    enrollStudentInCourse();
                    break;
                case 4:
                    viewAllCourses();
                    break;
                case 5:
                    viewAllStudents();
                    break;
                case 6:
                    viewAsStudent();
                    break;
                case 0:
                    view.showMessage("Exiting...");
                    running = false;
                    break;
                default:
                    view.showMessage("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void createCourse() {
        Program program = view.createCourse();
        if (program != null) {
            programs.add(program);
            for (Module module : program.getModulesTaught()) {
                modules.add(module);
                lecturers.add(module.getLecturer());
                for (Group group : module.getSessions()) {
                    groups.add(group);
                    sessions.add(group.getSession());
                }
            }
            view.showMessage("Course created successfully!");
        }
    }

    private void viewAllCourses() {
        if (programs.isEmpty()) {
            view.showMessage("No courses found");
            return;
        }
        view.showMessage("\n=== All Courses ===");
        for (Program p : programs) {
            view.showMessage("Course: " + p.getProgramName() + " (" + p.getModulesTaught().size() + " modules)");
            for (Module m : p.getModulesTaught()) {
                view.showMessage("  - " + m.getModuleCode() + ": " + m.getModuleName() + 
                                " (" + m.getSessions().size() + 1 + " sessions)");
            }
        }
    }

    private void viewAllStudents() {
        if (students.isEmpty()) {
            view.showMessage("No students found");
            return;
        }
        view.showMessage("\n=== All Students ===");
        for (Student s : students) {
            view.showMessage("ID: " + s.getStudentID() + " | Name: " + s.getName() + 
                           " | Age: " + s.getAge() + 
                           " | Course: " + (s.getProgram() != null ? s.getProgram().getProgramName() : "None") +
                           " | Year: " + s.getYearOfStudy());
        }
    }

    private void enrollStudentInCourse() {
        if (students.isEmpty()) {
            view.showMessage("No students available");
            return;
        }
        if (programs.isEmpty()) {
            view.showMessage("No courses available");
            return;
        }
        int studentID = view.selectStudent(students);
        String programName = view.selectProgram(programs);
        
        if (studentID == -1 || programName == null) {
            return;
        }
        
        Student student = null;
        for (Student s : students) {
            if (s.getStudentID() == studentID) {
                student = s;
                break;
            }
        }
        
        Program program = null;
        for (Program p : programs) {
            if (p.getProgramName().equals(programName)) {
                program = p;
                break;
            }
        }
            
        if (student == null || program == null) {
            return;
        }
        
        Random random = new Random();
        
        for (Module module : program.getModulesTaught()) {
            module.addEnrolledStudent(student);
            
            Map<Session, List<Group>> groupsBySession = new HashMap<>();
            for (Group group : module.getSessions()) {
                Session session = group.getSession();
                List<Group> sessionGroups = groupsBySession.computeIfAbsent(session, k -> new ArrayList<>());
                sessionGroups.add(group);
            }
            
            for (List<Group> sessionGroups : groupsBySession.values()) {
                if (!sessionGroups.isEmpty()) {
                    Group randomGroup = sessionGroups.get(random.nextInt(sessionGroups.size()));
                    try {
                        student.addGroup(randomGroup);
                    } catch (IllegalArgumentException ignored) {
                    }
                }
            }
        }
        
        try {
            student.setProgram(program);
            view.showMessage("Student " + student.getName() + " enrolled in course " + programName);
        } catch (IllegalArgumentException e) {
            view.showMessage("Error: " + e.getMessage());
        }
    }

    public void createStudent() {
        Student student = view.addStudent();
        if (student != null) {
            students.add(student);
            view.showMessage("Student created successfully");
        }
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public List<Program> getAllPrograms() {
        return new ArrayList<>(programs);
    }

    private void viewAsStudent() {
        if (students.isEmpty()) {
            view.showMessage("No students available");
            return;
        }
        int studentID = view.selectStudent(students);
        if (studentID == -1) {
            return;
        }
        
        Student student = null;
        for (Student s : students) {
            if (s.getStudentID() == studentID) {
                student = s;
                break;
            }
        }
        
        if (student == null) {
            view.showMessage("Student not found");
            return;
        }
        
        StudentConsoleView studentView = new StudentConsoleView();
        StudentController studentController = new StudentController(student, studentView);
        studentController.start();
    }

    /**
     * Load existing data from CSV files if available.
     */
    private void loadExistingData() {
        try {
            if (dbController.hasMissingData("Student") ||
                    dbController.hasMissingData("Module") ||
                    dbController.hasMissingData("Program")) {

                view.showMessage("Found existing data in CSV files. Loading...");

                DataLoader loader = new DataLoader(dbController);
                loader.loadAllData();

                // Populate lists from loaded data
                students.addAll(loader.getStudents());
                lecturers.addAll(loader.getLecturers());
                programs.addAll(loader.getPrograms());
                modules.addAll(loader.getModules());
                sessions.addAll(loader.getSessions());
                groups.addAll(loader.getGroups());

                view.showMessage("Successfully loaded data from CSV files!");
            }
        } catch (Exception e) {
            view.showMessage("Warning: Could not load existing data: " + e.getMessage());
        }
    }
}
