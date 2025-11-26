package views;

import models.*;
import models.Module;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class AdminConsoleView implements AdminView {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public int showMenu() {
        System.out.println("\n=== Admin Menu ===");
        System.out.println("1. Create Course (Program + Modules + Sessions)");
        System.out.println("2. Create Student");
        System.out.println("3. Enroll Student in Course");
        System.out.println("4. View All Courses");
        System.out.println("5. View All Students");
        System.out.println("6. View as Student");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
        return scanner.nextInt();
    }

    @Override
    public Student addStudent() {
        System.out.println("\n=== Create Student ===");
        scanner.nextLine();
        
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Student Age: ");
        int age = scanner.nextInt();
        
        scanner.nextLine();
        System.out.print("Enter Year of Study: ");
        int yearOfStudy = scanner.nextInt();
        
        Program tempProgram = new Program("Temp", 0);
        return new Student(name, age, tempProgram, yearOfStudy);
    }

    @Override
    public Program createCourse() {
        System.out.println("\n=== Create Course ===");
        scanner.nextLine();
        
        System.out.print("Enter Course Name: ");
        String courseName = scanner.nextLine();
        
        System.out.print("How many modules in this course? ");
        int moduleCount = scanner.nextInt();
        scanner.nextLine();
        
        Program program = new Program(courseName, moduleCount);
        
        for (int i = 0; i < moduleCount; i++) {
            System.out.println("\n--- Module " + (i + 1) + " ---");
            
            System.out.print("Module Code: ");
            String moduleCode = scanner.nextLine();
            
            System.out.print("Module Name: ");
            String moduleName = scanner.nextLine();
            
            System.out.print("Lecturer Name: ");
            String lecturerName = scanner.nextLine();
            
            System.out.print("Lecturer Age: ");
            int lecturerAge = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Lecturer Staff ID: ");
            String staffID = scanner.nextLine();
            
            System.out.print("Lecturer Department: ");
            String department = scanner.nextLine();
            
            System.out.print("Module Length (weeks): ");
            int lengthInWeeks = scanner.nextInt();
            
            System.out.print("Start Week: ");
            int startWeek = scanner.nextInt();
            scanner.nextLine();
            
            Lecturer lecturer = new Lecturer(lecturerName, lecturerAge, staffID, department);
            Module module = new Module(moduleCode, moduleName, lecturer, lengthInWeeks, startWeek);
            program.addModuleTaught(module);
            
            System.out.print("How many sessions (labs, lectures, tutorials) for this module per week? ");
            int sessionCount = scanner.nextInt();
            scanner.nextLine();
            
            for (int j = 0; j < sessionCount; j++) {
                System.out.println("\n  Session " + (j + 1) + ":");
                System.out.print("  Type (lecture/lab/tutorial): ");
                String sessionType = scanner.nextLine().toLowerCase();
                
                System.out.print("  Room Number: ");
                String roomNumber = scanner.nextLine();
                
                System.out.print("  Room Capacity: ");
                int capacity = scanner.nextInt();
                scanner.nextLine();
                
                Room.RoomType roomType;
                if (sessionType.equals("lecture")) {
                    roomType = Room.RoomType.CLASSROOM;
                } else {
                    roomType = Room.RoomType.LABORATORY;
                }
                
                System.out.print("  Building: ");
                String building = scanner.nextLine();
                
                System.out.print("  Day (MONDAY/TUESDAY/WEDNESDAY/THURSDAY/FRIDAY): ");
                String dayStr = scanner.nextLine().toUpperCase();
                Session.Day day = Session.Day.valueOf(dayStr);
                
                System.out.print("  Start Week: ");
                int sessionStartWeek = scanner.nextInt();
                
                System.out.print("  End Week: ");
                int endWeek = scanner.nextInt();
                
                System.out.print("  Start Time (HH:MM): ");
                String startTimeStr = scanner.next();
                LocalTime startTime = LocalTime.parse(startTimeStr);
                
                System.out.print("  End Time (HH:MM): ");
                String endTimeStr = scanner.next();
                LocalTime endTime = LocalTime.parse(endTimeStr);
                scanner.nextLine();
                
                Room room = new Room(roomNumber, capacity, roomType, building);
                Session session;

                switch (sessionType) {
                    case "lecture" -> {
                        session = new Lecture(module, lecturer, room, day, sessionStartWeek, startTime, endTime, endWeek);
                        new Group('A', session); // this kinda defeats the purpose of our group idea, im too tired.
                    }
                    case "lab" -> {
                        session = new Laboratory(module, lecturer, room, day, sessionStartWeek, startTime, endTime, endWeek);
                        System.out.print("  Group ID (A, B, C, etc.): ");
                        char groupID = scanner.nextLine().charAt(0);
                        new Group(groupID, session);
                    }
                    case "tutorial" -> {
                        session = new Tutorial(module, lecturer, room, day, sessionStartWeek, startTime, endTime, endWeek);
                        System.out.print("  Group ID (A, B, C, etc.): ");
                        char groupID = scanner.nextLine().charAt(0);
                        new Group(groupID, session);
                    }
                }
            }
        }
        
        return program;
    }

    @Override
    public String selectProgram(@NotNull List<Program> programs) {
        System.out.println("\n=== Select Course ===");
        for (int i = 0; i < programs.size(); i++) {
            System.out.println((i + 1) + ". " + programs.get(i).getProgramName());
        }
        System.out.print("Enter course number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice > 0 && choice <= programs.size()) {
            return programs.get(choice - 1).getProgramName();
        }
        return null;
    }

    @Override
    public int selectStudent(@NotNull List<Student> students) {
        System.out.println("\n=== Select Student ===");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". ID: " + students.get(i).getStudentID() + " - " + students.get(i).getName());
        }
        System.out.print("Enter student number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice > 0 && choice <= students.size()) {
            return students.get(choice - 1).getStudentID();
        }
        return -1;
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}
