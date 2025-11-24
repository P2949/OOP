package views;

import java.util.Scanner;

/**
 * The type Console view.
 */
public class ConsoleView implements View {

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Collect data required to create a Student.
     *
     * @return StudentInput holder containing raw user input
     */
    @Override
    public StudentInput addStudent() {
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

        return new StudentInput(id, firstName, lastName, age, program, yearOfStudy);
    }

    /**
     * Collect data required to create a Lecturer (Staff).
     *
     * @return LecturerInput holder
     */
    @Override
    public LecturerInput addLecturer() {
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

        return new LecturerInput(staffID, firstName, lastName, age, department, moduleTaught);
    }

    /**
     * Collect program creation input.
     *
     * @return ProgramInput holder
     */
    @Override
    public ProgramInput addProgram() {
        System.out.println("Enter Program Name:");
        String programName = scanner.next();

        System.out.println("Enter Number of Modules:");
        int moduleCount = scanner.nextInt();

        return new ProgramInput(programName, moduleCount);
    }

    /**
     * Collect module creation input.
     *
     * @return ModuleInput holder
     */
    @Override
    public ModuleInput addModule() {
        System.out.println("Enter Module Name: ");
        String moduleName = scanner.next();

        System.out.println("Enter Module Code: ");
        String moduleCode = scanner.next();

        System.out.println("Enter Module Lecturer: ");
        String moduleLecturer = scanner.next();

        System.out.println("Enter Module Length (in weeks): ");
        int moduleLength = scanner.nextInt();

        System.out.println("Enter Module Start Week: ");
        int moduleStartWeek = scanner.nextInt();

        return new ModuleInput(moduleName, moduleCode, moduleLecturer, moduleLength, moduleStartWeek);
    }

    /**
     * Collect session creation input for a chosen session type.
     *
     * @return SessionInput holder or null if the type is invalid
     */
    @Override
    public SessionInput addSession() {
        System.out.println("What kind of session would you like to add? (laboratory/tutorial/lecture)");
        String kindOfSession = scanner.next();

        int startWeek;
        int endWeek;
        String startTime;
        String endTime;

        switch (kindOfSession.toLowerCase()) {
            case "laboratory":
                System.out.println("Enter Start Week: ");
                startWeek = scanner.nextInt();

                System.out.println("Enter Start Time: ");
                startTime = scanner.next();

                System.out.println("Enter End Week: ");
                endWeek = scanner.nextInt();

                System.out.println("Enter End Time: ");
                endTime = scanner.next();
                break;

            case "tutorial":
                System.out.println("Enter Start Week: ");
                startWeek = scanner.nextInt();

                System.out.println("Enter Start Time: ");
                startTime = scanner.next();

                System.out.println("Enter End Week: ");
                endWeek = scanner.nextInt();

                System.out.println("Enter End Time: ");
                endTime = scanner.next();
                break;

            case "lecture":
                System.out.println("Enter Start Week: ");
                startWeek = scanner.nextInt();

                System.out.println("Enter Start Time: ");
                startTime = scanner.next();

                System.out.println("Enter End Week: ");
                endWeek = scanner.nextInt();

                System.out.println("Enter End Time: ");
                endTime = scanner.next();
                break;

            default:
                System.out.println("Invalid session type.");
                return null;
        }

        return new SessionInput(kindOfSession, startWeek, endWeek, startTime, endTime);
    }

    /**
     * Simple POD for student creation input.
     */
    public record StudentInput(int id, String firstName, String lastName, int age, String program, int yearOfStudy) {

        /**
         * Construct an input holder.
         *
         * @param id          student id entered
         * @param firstName   first name
         * @param lastName    last name
         * @param age         age
         * @param program     program name text
         * @param yearOfStudy year integer
         */
        public StudentInput {
        }
    }

    /**
     * Simple POD for lecturer creation input.
     */
    public record LecturerInput(String staffID, String firstName, String lastName, int age, String department,
                                String moduleTaught) {

    }

    /**
     * Simple POD for program creation input.
     */
    public record ProgramInput(String programName, int moduleCount) {

    }

    /**
     * Simple POD for module creation input.
     */
    public record ModuleInput(String moduleName, String moduleCode, String moduleLecturer, int moduleLength,
                              int moduleStartWeek) {

    }

    /**
     * Simple POD for session creation input.
     */
    public record SessionInput(String kind, int startWeek, int endWeek, String startTime, String endTime) {

    }
}
