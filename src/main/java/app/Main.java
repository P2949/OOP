package app;

import controllers.AdminController;
import controllers.StudentController;
import models.Student;
import views.AdminConsoleView;
import views.StudentConsoleView;

import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Are you an admin or student? (admin/student)");
        String userType = scanner.next().toLowerCase();
        scanner.nextLine();

        if (userType.equals("admin")) {
            AdminConsoleView adminView = new AdminConsoleView();
            AdminController adminController = new AdminController(adminView);
            adminController.start();
        } else if (userType.equals("student")) {
            System.out.println("Enter student ID:");
            int studentID = scanner.nextInt();
            Student student = null;
            StudentConsoleView studentView = new StudentConsoleView();
            StudentController studentController = new StudentController(student, studentView);
            studentController.start();
        } else {
            System.out.println("Invalid option");
        }
    }
}
