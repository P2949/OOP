package views;

public interface View {

    /**
     * Request input for creating a student.
     *
     * @return a data holder containing student input
     */
    ConsoleView.StudentInput addStudent();

    /**
     * Request input for creating a lecturer.
     *
     * @return lecturer input holder
     */
    ConsoleView.LecturerInput addLecturer();

    /**
     * Request input for creating a program.
     *
     * @return program input holder
     */
    ConsoleView.ProgramInput addProgram();

    /**
     * Request input for creating a module.
     *
     * @return module input holder
     */
    ConsoleView.ModuleInput addModule();

    /**
     * Request input for creating a session.
     *
     * @return session input holder
     */
    ConsoleView.SessionInput addSession();

    /**
     * Show message to the user
     *
     * @param message text to display
     */
    default void showMessage(String message) {
        System.out.println(message);
    }
}
