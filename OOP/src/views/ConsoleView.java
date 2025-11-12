package views;
import java.time.LocalTime;
import java.util.Scanner;

public class ConsoleView implements View {
    Scanner scanner = new Scanner(System.in);
    public void addStudent() {
        System.out.println("Enter Student ID: ");
        int id = scanner.nextInt();
        System.out.println("Enter Student First Name: ");
        String firstName = scanner.next();
        System.out.println("Enter Student Last Name: ");
        String lastName = scanner.next();
        System.out.println("Enter Student Age: ");
        int age = scanner.nextInt();
        System.out.println("Enter Student Program: ");
        String program = scanner.next();
        System.out.println("Enter Student Year of Study: ");
        int yearOfStudy = scanner.nextInt();
    }

    public void addLecturer(){
        System.out.println("Enter Staff ID: ");
        String staffID = scanner.next();
        System.out.println("Enter Staff First Name: ");
        String firstName = scanner.next();
        System.out.println("Enter Staff Last Name: ");
        String lastName = scanner.next();
        System.out.println("Enter Staff Age: ");
        int age = scanner.nextInt();
        System.out.println("Enter Department: ");
        String department = scanner.next();
        System.out.println("Enter Module Taught: ");
        String moduleTaught = scanner.next();
    }

    public void addProgram(){
        System.out.println("Enter Module code: ");
        String module = scanner.next();
    }

    public void addModule(){
        System.out.println("Enter Module Name: ");
        String moduleName = scanner.next();
        System.out.println("Enter Module Code: ");
        String moduleCode = scanner.next();
        System.out.println("Enter Module Lecturer: ");
        String moduleLecturer = scanner.next();
        System.out.println("Enter Module length (in weeks): ");
        int moduleLength = scanner.nextInt();
        System.out.println("Enter Module Start Week: ");
        int moduleStartWeek = scanner.nextInt();
    }

    public void addSession(){
        System.out.println("What kind of session would you like to add?");
        String kindOfSession = scanner.next();
        switch (kindOfSession) {
            case "laboratory":
                System.out.println("Enter Start Week: ");
                int labStartWeek = scanner.nextInt();
                System.out.println("Enter Start Time: ");
                String labStartTime = scanner.next();
                System.out.println("Enter End Week: ");
                int labEndWeek = scanner.nextInt();
                System.out.println("Enter End Time: ");
                String labEndTime = scanner.next();
            case "tutorial":
                System.out.println("Enter Start Week: ");
                int tutStartWeek = scanner.nextInt();
                System.out.println("Enter Start Time: ");
                String tutStartTime = scanner.next();
                System.out.println("Enter End Week: ");
                int tutEndWeek = scanner.nextInt();
                System.out.println("Enter End Time: ");
                String tutEndTime = scanner.next();
            case "lecture":
                System.out.println("Enter Start Week: ");
                int lecStartWeek = scanner.nextInt();
                System.out.println("Enter Start Time: ");
                String lecStartTime = scanner.next();
                System.out.println("Enter End Week: ");
                int lecEndWeek = scanner.nextInt();
                System.out.println("Enter End Time: ");
                String lecEndTime = scanner.next();

        }
    }
}
