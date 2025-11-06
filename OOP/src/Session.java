
/* Session.java
 * 
 * Represents a session in the system.
 * Contains attributes such as module and lecturer.
 */
abstract class Session {

    private Module module;
    private Room room;
    private Lecturer lecturer;

    public Session(Module module, Lecturer lecturer, Room room) {
        this.module = module;
        this.lecturer = lecturer;
        this.room = room;
        this.lecturer.addModuleTaught(this.module);
    }

    public Room getRoom() {
        return this.room;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Module getModule() {
        return this.module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
