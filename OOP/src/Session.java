
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
    }

    public Room getRoom() {
        return new Room(this.room.getRoomNumber(), this.room.getCapacity(), this.room.getType(), this.room.getBuilding());
    }

    public Lecturer getLecturer() {
        return new Lecturer(this.lecturer.getName(), this.lecturer.getAge(), this.lecturer.getStaffID(), this.lecturer.getDepartment());
    }

    public void setRoom(Room room) {
        this.room = new Room(room.getRoomNumber(), room.getCapacity(), room.getType(), room.getBuilding());
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = new Lecturer(lecturer.getName(), lecturer.getAge(), lecturer.getStaffID(), lecturer.getDepartment());
    }

    public Module getModule() {
        return new Module(this.module.getModuleCode(), this.module.getModuleName(), this.module.getLecturer(), this.module.getLengthInWeeks(), this.module.getStartWeek());
    }

    public void setModule(Module module) {
        this.module = new Module(module.getModuleCode(), module.getModuleName(), module.getLecturer(), module.getLengthInWeeks(), module.getStartWeek());
    }
}
