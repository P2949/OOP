package controllers;

import models.*;
import java.time.LocalTime;

public class TestSave {
    public static void main(String[] args) {
        try {
            DatabaseController db = new DatabaseController();
            
            System.out.println("Testing CSV Persistence\n");
            
            System.out.println("Creating Rooms...");
            Room room1 = new Room("101", 50, Room.RoomType.CLASSROOM, "Building A");
            Room room2 = new Room("L201", 30, Room.RoomType.LABORATORY, "CS Building");
            room1.setOccupied(true);
            db.save(room1);
            db.save(room2);
            System.out.println("Saved: " + room1.getRoomNumber() + ", " + room2.getRoomNumber());
            
            System.out.println("\nCreating Lecturer...");
            Lecturer lecturer = new Lecturer("Dr. English", 45, "L001", "Computer Science");
            db.save(lecturer);
            System.out.println("Saved: " + lecturer.getName() + " (" + lecturer.getStaffID() + ")");
            
            System.out.println("\nCreating Module...");
            models.Module module = new models.Module("WD1004", "Web Development", lecturer, 12, 1);
            db.save(module);
            System.out.println("Saved: " + module.getModuleCode() + " - " + module.getModuleName());
            
            System.out.println("\nCreating Program...");
            Program program = new Program("Computer Science", 3);
            program.addModuleTaught(module);
            db.save(program);
            System.out.println("Saved: " + program.getProgramName() + " with " + program.getTotalModules() + " modules");
            
            System.out.println("\nCreating Students...");
            Student student1 = new Student("Pedro", 20, program, 1);
            Student student2 = new Student("Shay", 21, program, 1);
            Student student3 = new Student("Caoimhe", 80, program, 1);
            student1.setStudentID(2024001);
            student2.setStudentID(2024002);
            student3.setStudentID(2024003);
            module.addEnrolledStudent(student1);
            module.addEnrolledStudent(student2);
            module.addEnrolledStudent(student3);
            db.save(student1);
            db.save(student2);
            db.save(student3);
            System.out.println("Saved: " + student1.getName() + ", " + student2.getName() + ", " + student3.getName());
            
            System.out.println("\nCreating Sessions...");
            Lecture lecture = new Lecture(module, lecturer, room1, Session.Day.MONDAY, 1, 
                LocalTime.of(9, 0), LocalTime.of(10, 0), 12);
            Laboratory lab = new Laboratory(module, lecturer, room2, Session.Day.TUESDAY, 1, 
                LocalTime.of(14, 0), LocalTime.of(16, 0), 12);
            Tutorial tutorial = new Tutorial(module, lecturer, room1, Session.Day.WEDNESDAY, 1, 
                LocalTime.of(11, 0), LocalTime.of(12, 0), 12);
            db.save(lecture);
            db.save(lab);
            db.save(tutorial);
            System.out.println("Saved: Lecture, Laboratory, Tutorial");
            
            System.out.println("\nCreating Groups...");
            Group group1 = new Group('A', lecture);
            Group group2 = new Group('A', lab);
            Group group3 = new Group('B', lab);
            Group group4 = new Group('A', tutorial);
            
            student1.addGroup(group1);
            student1.addGroup(group2);
            student1.addGroup(group4);
            student2.addGroup(group1);
            student2.addGroup(group2);
            student2.addGroup(group4);
            student3.addGroup(group1);
            student3.addGroup(group3);
            student3.addGroup(group4);
            
            db.save(group1);
            db.save(group2);
            db.save(group3);
            db.save(group4);
            System.out.println("Saved: Groups A, B with students assigned");
            
            System.out.println("\nUpdating Students with Groups...");
            db.save(student1);
            db.save(student2);
            db.save(student3);
            System.out.println("Updated students with group relationships");
            
            System.out.println("\nUpdating Module with Sessions and Students...");
            db.save(module);
            System.out.println("Updated module with relationships");
            
            System.out.println("\nUpdating Lecturer with Modules...");
            db.save(lecturer);
            System.out.println("Updated lecturer with modules taught");
            System.out.println("\nAll CSV Files created");

            
        } catch (Exception e) {
            System.err.println("\nError: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

